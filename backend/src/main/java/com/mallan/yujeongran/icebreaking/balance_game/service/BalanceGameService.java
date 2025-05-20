package com.mallan.yujeongran.icebreaking.balance_game.service;

import com.mallan.yujeongran.icebreaking.balance_game.dto.request.StartBalanceGameRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.SubmitBalanceAnswerRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceFinalResultResponseDto;
import com.mallan.yujeongran.icebreaking.balance_game.entity.BalanceQuestion;
import com.mallan.yujeongran.icebreaking.balance_game.entity.BalanceResult;
import com.mallan.yujeongran.icebreaking.balance_game.entity.BalanceRoom;
import com.mallan.yujeongran.icebreaking.balance_game.repository.BalanceQuestionRepository;
import com.mallan.yujeongran.icebreaking.balance_game.repository.BalanceResultRepository;
import com.mallan.yujeongran.icebreaking.balance_game.repository.BalanceRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BalanceGameService {

    private final BalanceRoomRepository balanceRoomRepository;
    private final BalanceResultRepository balanceResultRepository;
    private final BalanceQuestionRepository balanceQuestionRepository;
    private final BalancePlayerService balancePlayerService;
    private final StringRedisTemplate stringRedisTemplate;

    public void startGame(String roomCode, StartBalanceGameRequestDto requestDto) {
        BalanceRoom room = balanceRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다."));

        if (!balancePlayerService.isHost(roomCode, requestDto.getPlayerId())) {
            throw new IllegalArgumentException("방장만 게임을 시작할 수 있습니다.");
        }

        List<BalanceQuestion> allQuestions = balanceQuestionRepository.findByTopicId(room.getTopicId());
        if (allQuestions.size() < room.getQuestionCount()) {
            throw new IllegalArgumentException("선택한 문항 수보다 주제에 등록된 문항이 부족합니다.");
        }

        Collections.shuffle(allQuestions);
        List<BalanceQuestion> selectedQuestions = allQuestions.subList(0, room.getQuestionCount());

        String redisKey = "room:" + roomCode + ":questionIds";
        for (BalanceQuestion q : selectedQuestions) {
            stringRedisTemplate.opsForList().rightPush(redisKey, q.getId().toString());
        }

        stringRedisTemplate.opsForValue().set("room:" + roomCode + ":currentQuestionIdx", "0");

    }

    public void submitAnswer(String roomCode, Long questionId, SubmitBalanceAnswerRequestDto request) {
        String redisKey = "room:" + roomCode + ":question:" + questionId + ":result";
        String choice = request.getSelectedChoice();

        if (!choice.equals("A") && !choice.equals("B")) {
            throw new IllegalArgumentException("선택지는 A 또는 B만 가능합니다.");
        }

        // 중복 제출 방지 (선택 사항)
        String submitKey = "room:" + roomCode + ":question:" + questionId + ":submitted:" + request.getPlayerId();
        Boolean alreadySubmitted = stringRedisTemplate.hasKey(submitKey);
        if (alreadySubmitted) {
            throw new IllegalArgumentException("이미 해당 질문에 응답하였습니다.");
        }

        // 답변 수 저장
        stringRedisTemplate.opsForHash().increment(redisKey, choice, 1);

        // 제출 여부 플래그 저장 (TTL은 질문 하나당 제한 시간만큼 걸어도 좋음)
        stringRedisTemplate.opsForValue().set(submitKey, "true", Duration.ofMinutes(10));
    }


    public Map<String, Integer> getQuestionResult(String roomCode, Long questionId) {
        String redisKey = "room:" + roomCode + ":question:" + questionId + ":result";

        Map<Object, Object> raw = stringRedisTemplate.opsForHash().entries(redisKey);
        int countA = Integer.parseInt(String.valueOf(raw.getOrDefault("A", "0")));
        int countB = Integer.parseInt(String.valueOf(raw.getOrDefault("B", "0")));

        Map<String, Integer> result = new HashMap<>();
        result.put("A", countA);
        result.put("B", countB);
        return result;
    }

    public void saveFinalResult(String roomCode) {
        BalanceRoom room = balanceRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("방이 존재하지 않습니다."));

        String redisQuestionKey = "room:" + roomCode + ":questionIds";
        List<String> questionIds = stringRedisTemplate.opsForList().range(redisQuestionKey, 0, -1);
        if (questionIds == null) return;

        for (String questionIdStr : questionIds) {
            Long questionId = Long.parseLong(questionIdStr);
            BalanceQuestion question = balanceQuestionRepository.findById(questionId)
                    .orElseThrow(() -> new IllegalArgumentException("질문이 존재하지 않습니다."));

            Map<Object, Object> resultMap = stringRedisTemplate.opsForHash().entries(
                    "room:" + roomCode + ":question:" + questionId + ":result"
            );

            int countA = Integer.parseInt(String.valueOf(resultMap.getOrDefault("A", "0")));
            int countB = Integer.parseInt(String.valueOf(resultMap.getOrDefault("B", "0")));

            BalanceResult result = BalanceResult.builder()
                    .room(room)
                    .question(question)
                    .countChoiceA(countA)
                    .countChoiceB(countB)
                    .build();

            balanceResultRepository.save(result);
        }
    }

    public List<BalanceFinalResultResponseDto> getFinalResult(String roomCode) {
        BalanceRoom room = balanceRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("방이 존재하지 않습니다."));

        List<BalanceResult> results = balanceResultRepository.findByRoom(room);

        return results.stream()
                .map(result -> BalanceFinalResultResponseDto.builder()
                        .questionId(result.getQuestion().getId())
                        .content(result.getQuestion().getContent())
                        .countA(result.getCountChoiceA())
                        .countB(result.getCountChoiceB())
                        .build())
                .collect(Collectors.toList());
    }

    public void restartGame(String roomCode) {
        BalanceRoom room = balanceRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다."));

        String questionIdsKey = "room:" + roomCode + ":questionIds";
        String currentIdxKey = "room:" + roomCode + ":currentQuestionIdx";

        List<String> questionIds = stringRedisTemplate.opsForList().range(questionIdsKey, 0, -1);
        if (questionIds != null) {
            for (String qid : questionIds) {
                stringRedisTemplate.delete("room:" + roomCode + ":question:" + qid + ":result");

                String submitPrefix = "room:" + roomCode + ":question:" + qid + ":submitted:";
                Set<String> submittedKeys = stringRedisTemplate.keys(submitPrefix + "*");
                if (submittedKeys != null) {
                    stringRedisTemplate.delete(submittedKeys);
                }
            }
        }

        stringRedisTemplate.delete(questionIdsKey);
        stringRedisTemplate.delete(currentIdxKey);

        List<BalanceResult> oldResults = balanceResultRepository.findByRoom(room);
        balanceResultRepository.deleteAll(oldResults);

        room.setTopicId(null);
        room.setQuestionCount(0);
    }

    public void deleteGame(String roomCode) {
        BalanceRoom room = balanceRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("방이 존재하지 않습니다."));

        balancePlayerService.deleteRoom(roomCode);
        balanceResultRepository.deleteAll(balanceResultRepository.findByRoom(room));
        balanceRoomRepository.delete(room);
    }


}
