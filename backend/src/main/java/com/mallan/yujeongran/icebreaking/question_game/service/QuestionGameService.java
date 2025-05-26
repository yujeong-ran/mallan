package com.mallan.yujeongran.icebreaking.question_game.service;

import com.mallan.yujeongran.icebreaking.question_game.dto.request.QuestionEndGameRequestDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.request.QuestionRestartGameRequestDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.request.QuestionSelectAnswererRequestDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.request.QuestionSubmitAnswerRequestDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.response.*;
import com.mallan.yujeongran.icebreaking.question_game.entity.QuestionQuestion;
import com.mallan.yujeongran.icebreaking.question_game.entity.QuestionTopic;
import com.mallan.yujeongran.icebreaking.question_game.repository.QuestionQuestionRepository;
import com.mallan.yujeongran.icebreaking.question_game.repository.QuestionRoomRepository;
import com.mallan.yujeongran.icebreaking.question_game.repository.QuestionTopicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionGameService {

    private final RedisTemplate<String, String> redisTemplate;
    private final QuestionTopicRepository questiontopicRepository;
    private final QuestionQuestionRepository questionquestionRepository;
    private final QuestionRoomRepository questionRoomRepository;

    public void startGame(String roomCode, int topicId, int questionCount) {
        QuestionTopic topic = questiontopicRepository.findById((long) topicId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주제를 찾을 수 없습니다."));

        List<QuestionQuestion> questions = questionquestionRepository.findByTopicId(topicId);
        Collections.shuffle(questions);

        int count = Math.min(questionCount, questions.size());
        for (int i = 0; i < count; i++) {
            redisTemplate.opsForList().rightPush("question:room:" + roomCode + ":questions", String.valueOf(questions.get(i).getId()));
        }
    }

    public QuestionDrawResponseDto drawQuestion(String roomCode) {
        String questionId = redisTemplate.opsForList().leftPop("question:room:" + roomCode + ":questions");
        if (questionId == null) {
            throw new IllegalStateException("더 이상 남은 질문이 없습니다.");
        }

        redisTemplate.opsForValue().set("question:room:" + roomCode + ":currentQuestionId", questionId);

        QuestionQuestion question = questionquestionRepository.findById(Long.parseLong(questionId))
                .orElseThrow(() -> new IllegalArgumentException("해당 질문을 찾을 수 없습니다."));

        return QuestionDrawResponseDto.builder()
                .questionId(question.getId())
                .questionContent(question.getContent())
                .build();
    }

    public void selectAnswerer(String roomCode, QuestionSelectAnswererRequestDto request) {
        redisTemplate.opsForValue().set("question:room:" + roomCode + ":questionerId", request.getQuestionerId());
        redisTemplate.opsForValue().set("question:room:" + roomCode + ":answererId", request.getAnswererId());
    }

    public void submitAnswer(String roomCode, QuestionSubmitAnswerRequestDto request) {
        String currentAnswerer = redisTemplate.opsForValue().get("question:room:" + roomCode + ":answererId");

        if (!request.getPlayerId().equals(currentAnswerer)) {
            throw new IllegalArgumentException("해당 플레이어는 답변자가 아닙니다.");
        }

        redisTemplate.opsForValue().set("question:room:" + roomCode + ":nextQuestionerId", currentAnswerer);

        redisTemplate.delete("question:room:" + roomCode + ":answererId");
        redisTemplate.delete("question:room:" + roomCode + ":questionerId");
        redisTemplate.delete("question:room:" + roomCode + ":currentQuestionId");
    }

    public void cleanupGameData(String roomCode) {
        redisTemplate.delete("question:room:" + roomCode + ":questions");
        redisTemplate.delete("question:room:" + roomCode + ":currentQuestionId");
        redisTemplate.delete("question:room:" + roomCode + ":questionerId");
        redisTemplate.delete("question:room:" + roomCode + ":answererId");
        redisTemplate.delete("question:room:" + roomCode + ":nextQuestionerId");
    }

    public boolean isGameFinished(String roomCode) {
        Long remaining = redisTemplate.opsForList().size("question:room:" + roomCode + ":questions");
        return remaining == null || remaining == 0;
    }

    public void restartGame(String roomCode, QuestionRestartGameRequestDto request, int topicId, int questionCount) {
        String hostId = (String) redisTemplate.opsForHash().get("question:room:" + roomCode, "hostId");

        if (!request.getPlayerId().equals(hostId)) {
            throw new IllegalArgumentException("방장만 게임을 재시작할 수 있습니다.");
        }

        cleanupGameData(roomCode);
        startGame(roomCode, topicId, questionCount);
    }

    public void endGame(String roomCode, QuestionEndGameRequestDto request) {
        String hostId = (String) redisTemplate.opsForHash().get("question:room:" + roomCode, "hostId");

        if (!request.getPlayerId().equals(hostId)) {
            throw new IllegalArgumentException("방장만 게임을 종료할 수 있습니다.");
        }

        cleanupGameData(roomCode);
        redisTemplate.delete("question:room:" + roomCode);
        redisTemplate.delete("question:room:" + roomCode + ":players");

        List<String> playerIds = redisTemplate.opsForList().range("question:room:" + roomCode + ":players", 0, -1);
        if (playerIds != null) {
            for (String playerId : playerIds) {
                redisTemplate.delete("question:player:" + playerId + ":nickname");
                redisTemplate.delete("question:player:" + playerId + ":profileImage");
            }
        }

        questionRoomRepository.findByRoomCode(roomCode)
                .ifPresent(questionRoomRepository::delete);
    }

    public QuestionSelectablePlayerListResponseDto getSelectablePlayers(String roomCode, String questionerId) {
        List<String> allPlayers = redisTemplate.opsForList().range("question:room:" + roomCode + ":players", 0, -1);

        if (allPlayers == null) {
            throw new IllegalArgumentException("플레이어 목록을 불러올 수 없습니다.");
        }

        List<QuestionSelectablePlayerResponseDto> filtered = allPlayers.stream()
                .filter(id -> !id.equals(questionerId))
                .map(id -> QuestionSelectablePlayerResponseDto.builder()
                        .playerId(id)
                        .nickname(redisTemplate.opsForValue().get("question:player:" + id + ":nickname"))
                        .profileImage(redisTemplate.opsForValue().get("question:player:" + id + ":profileImage"))
                        .build())
                .toList();

        return QuestionSelectablePlayerListResponseDto.builder()
                .players(filtered)
                .build();
    }

    public QuestionCurrentQuestionStateResponseDto getCurrentQuestionState(String roomCode) {
        String questionerId = redisTemplate.opsForValue().get("question:room:" + roomCode + ":questionerId");
        String answererId = redisTemplate.opsForValue().get("question:room:" + roomCode + ":answererId");
        String questionId = redisTemplate.opsForValue().get("question:room:" + roomCode + ":currentQuestionId");

        if (questionerId == null || answererId == null || questionId == null) {
            throw new IllegalStateException("진행 중인 질문 정보가 없습니다.");
        }

        String questionerNickname = redisTemplate.opsForValue().get("question:player:" + questionerId + ":nickname");
        String answererNickname = redisTemplate.opsForValue().get("question:player:" + answererId + ":nickname");

        QuestionQuestion question = questionquestionRepository.findById(Long.parseLong(questionId))
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

        return QuestionCurrentQuestionStateResponseDto.builder()
                .questionerId(questionerId)
                .questionerNickname(questionerNickname)
                .questionId(question.getId())
                .questionContent(question.getContent())
                .answererId(answererId)
                .answererNickname(answererNickname)
                .build();
    }

    public QuestionPlayerRoleResponseDto getPlayerRole(String roomCode, String playerId) {
        String questionerId = redisTemplate.opsForValue().get("question:room:" + roomCode + ":questionerId");
        String answererId = redisTemplate.opsForValue().get("question:room:" + roomCode + ":answererId");

        if (playerId.equals(questionerId)) {
            return QuestionPlayerRoleResponseDto.builder().role("questioner").build();
        } else if (playerId.equals(answererId)) {
            return QuestionPlayerRoleResponseDto.builder().role("answerer").build();
        } else {
            return QuestionPlayerRoleResponseDto.builder().role("none").build();
        }
    }

}
