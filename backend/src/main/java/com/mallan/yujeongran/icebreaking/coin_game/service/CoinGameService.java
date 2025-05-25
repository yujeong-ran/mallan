package com.mallan.yujeongran.icebreaking.coin_game.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mallan.yujeongran.icebreaking.coin_game.dto.request.CoinSubmitAnswerRequestDto;
import com.mallan.yujeongran.icebreaking.coin_game.dto.request.CoinSubmitQuestionRequestDto;
import com.mallan.yujeongran.icebreaking.coin_game.dto.response.CoinFinalResultListResponseDto;
import com.mallan.yujeongran.icebreaking.coin_game.dto.response.CoinFinalResultResponseDto;
import com.mallan.yujeongran.icebreaking.coin_game.dto.response.CoinQuestionResultResponseDto;
import com.mallan.yujeongran.icebreaking.coin_game.entity.CoinAnswer;
import com.mallan.yujeongran.icebreaking.coin_game.entity.CoinFinalResult;
import com.mallan.yujeongran.icebreaking.coin_game.entity.CoinQuestion;
import com.mallan.yujeongran.icebreaking.coin_game.entity.CoinQuestionResult;
import com.mallan.yujeongran.icebreaking.coin_game.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CoinGameService {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final CoinQuestionRepository coinQuestionRepository;
    private final CoinAnswerRepository coinAnswerRepository;
    private final CoinQuestionResultRepository coinQuestionResultRepository;
    private final CoinFinalResultRepository coinFinalResultRepository;
    private final CoinRoomRepository coinRoomRepository;

    public void submitQuestion(String roomCode, CoinSubmitQuestionRequestDto request) {
        CoinQuestion question = CoinQuestion.builder()
                .question(request.getQuestion())
                .expectedCount(request.getExpectedYesCount())
                .createdAt(LocalDateTime.now())
                .build();
        coinQuestionRepository.save(question);

        redisTemplate.opsForValue().set("coin:room:" + roomCode + ":currentQuestionId", question.getId().toString());
        redisTemplate.opsForValue().set("coin:room:" + roomCode + ":currentQuestionOwner", request.getPlayerId());
    }

    public void submitAnswer(String roomCode, CoinSubmitAnswerRequestDto request) {
        String questionId = redisTemplate.opsForValue().get("coin:room:" + roomCode + ":currentQuestionId");

        CoinAnswer answer = CoinAnswer.builder()
                .questionId(Long.parseLong(questionId))
                .playerId(request.getPlayerId())
                .answer(request.getAnswer())
                .createdAt(LocalDateTime.now())
                .build();

        coinAnswerRepository.save(answer);

        redisTemplate.opsForList().rightPush("coin:room:" + roomCode + ":currentAnswers", request.getPlayerId());
    }

    public boolean isAllAnswersSubmitted(String roomCode) {
        String questionOwner = redisTemplate.opsForValue().get("coin:room:" + roomCode + ":currentQuestionOwner");

        List<String> allPlayers = redisTemplate.opsForList().range("coin:room:" + roomCode + ":players", 0, -1);
        List<String> answeredPlayers = redisTemplate.opsForList().range("coin:room:" + roomCode + ":currentAnswers", 0, -1);

        long expectedAnswers = allPlayers.stream().filter(id -> !id.equals(questionOwner)).count();
        return answeredPlayers != null && answeredPlayers.size() >= expectedAnswers;
    }

    public CoinQuestionResultResponseDto completeQuestionAndReturnResult(String roomCode) {
        String resultKey = "coin:room:" + roomCode + ":lastResult";

        try {
            String resultJson = redisTemplate.opsForValue().get(resultKey);
            if (resultJson != null) {
                return objectMapper.readValue(resultJson, CoinQuestionResultResponseDto.class);
            }

            Long questionId = Long.parseLong(redisTemplate.opsForValue().get("coin:room:" + roomCode + ":currentQuestionId"));
            String questionOwner = redisTemplate.opsForValue().get("coin:room:" + roomCode + ":currentQuestionOwner");

            List<CoinAnswer> answers = coinAnswerRepository.findByQuestionId(questionId);

            long yesCount = answers.stream().filter(a -> a.getAnswer().equalsIgnoreCase("YES")).count();
            long noCount = answers.size() - yesCount;

            CoinQuestion question = coinQuestionRepository.findById(questionId).orElseThrow();
            boolean isCorrect = (int) yesCount == question.getExpectedCount();

            CoinQuestionResult result = CoinQuestionResult.builder()
                    .questionId(questionId)
                    .yesCount((int) yesCount)
                    .noCount((int) noCount)
                    .isCorrect(isCorrect)
                    .build();
            coinQuestionResultRepository.save(result);

            int scoreAwarded = 0;
            if (isCorrect) {
                redisTemplate.opsForValue().increment("coin:room:" + roomCode + ":score:" + questionOwner);
                scoreAwarded = 1;
            }

            CoinQuestionResultResponseDto responseDto = CoinQuestionResultResponseDto.builder()
                    .yesCount((int) yesCount)
                    .noCount((int) noCount)
                    .expectedYesCount(question.getExpectedCount())
                    .isCorrect(isCorrect)
                    .scoreAwarded(scoreAwarded)
                    .build();

            String json = objectMapper.writeValueAsString(responseDto);
            redisTemplate.opsForValue().set(resultKey, json);

            redisTemplate.delete("coin:room:" + roomCode + ":currentAnswers");
            redisTemplate.delete("coin:room:" + roomCode + ":currentQuestionId");
            redisTemplate.delete("coin:room:" + roomCode + ":currentQuestionOwner");

            return responseDto;

        } catch (Exception e) {
            throw new RuntimeException("질문 결과 생성 중 오류 발생", e);
        }
    }

    public CoinFinalResultListResponseDto getFinalResults(String roomCode) {
        List<String> playerIds = redisTemplate.opsForList().range("coin:room:" + roomCode + ":players", 0, -1);

        List<CoinFinalResultResponseDto> results = playerIds.stream().map(playerId -> {
            String nickname = redisTemplate.opsForValue().get("coin:player:" + playerId + ":nickname");
            String profileImage = redisTemplate.opsForValue().get("coin:player:" + playerId + ":profileImage");
            String scoreStr = redisTemplate.opsForValue().get("coin:room:" + roomCode + ":score:" + playerId);
            int score = scoreStr == null ? 0 : Integer.parseInt(scoreStr);

            CoinFinalResult finalResult = CoinFinalResult.builder()
                    .roomCode(roomCode)
                    .playerId(playerId)
                    .nickname(nickname)
                    .score(score)
                    .profileImage(profileImage)
                    .createdAt(LocalDateTime.now())
                    .build();

            coinFinalResultRepository.save(finalResult);

            return CoinFinalResultResponseDto.builder()
                    .playerId(playerId)
                    .nickname(nickname)
                    .score(score)
                    .profileImage(profileImage)
                    .build();
        }).sorted((a, b) -> b.getScore() - a.getScore()).toList();

        return CoinFinalResultListResponseDto.builder()
                .results(results)
                .build();
    }

    public void restartGame(String roomCode) {
        redisTemplate.delete("coin:room:" + roomCode + ":currentQuestionId");
        redisTemplate.delete("coin:room:" + roomCode + ":currentQuestionOwner");
        redisTemplate.delete("coin:room:" + roomCode + ":currentAnswers");

        redisTemplate.delete("coin:room:" + roomCode + ":lastResult");

        List<String> players = redisTemplate.opsForList().range("coin:room:" + roomCode + ":players", 0, -1);
        if (players != null) {
            for (String playerId : players) {
                redisTemplate.delete("coin:room:" + roomCode + ":score:" + playerId);
            }
        }
    }

    public void endGame(String roomCode) {
        List<String> players = redisTemplate.opsForList().range("coin:room:" + roomCode + ":players", 0, -1);
        if (players != null) {
            for (String playerId : players) {
                redisTemplate.delete("coin:player:" + playerId + ":nickname");
                redisTemplate.delete("coin:player:" + playerId + ":profileImage");
                redisTemplate.delete("coin:room:" + roomCode + ":score:" + playerId);
            }
        }

        redisTemplate.delete("coin:room:" + roomCode + ":lastResult");

        redisTemplate.delete("coin:room:" + roomCode);
        redisTemplate.delete("coin:room:" + roomCode + ":players");
        redisTemplate.delete("coin:room:" + roomCode + ":currentQuestionId");
        redisTemplate.delete("coin:room:" + roomCode + ":currentQuestionOwner");
        redisTemplate.delete("coin:room:" + roomCode + ":currentAnswers");

        coinFinalResultRepository.deleteAll(coinFinalResultRepository.findByRoomCodeOrderByScoreDesc(roomCode));
        coinQuestionResultRepository.deleteAll();
        coinAnswerRepository.deleteAll();
        coinQuestionRepository.deleteAll();
        coinRoomRepository.findByRoomCode(roomCode).ifPresent(coinRoomRepository::delete);
    }


}
