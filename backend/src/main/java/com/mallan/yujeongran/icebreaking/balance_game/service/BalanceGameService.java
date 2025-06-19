package com.mallan.yujeongran.icebreaking.balance_game.service;

import com.mallan.yujeongran.icebreaking.admin.service.ManagementInfoService;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.BalanceCreateIngameQuestionRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.BalanceStartGameRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.BalanceSubmitAnswerRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceCreateIngameQuestionResponseDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceFinalResultResponseDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceQuestionResultResponseDto;
import com.mallan.yujeongran.icebreaking.balance_game.entity.BalanceQuestion;
import com.mallan.yujeongran.icebreaking.balance_game.entity.BalanceQuestionResult;
import com.mallan.yujeongran.icebreaking.balance_game.entity.BalanceRoom;
import com.mallan.yujeongran.icebreaking.balance_game.repository.BalanceFinalResultRepository;
import com.mallan.yujeongran.icebreaking.balance_game.repository.BalanceQuestionRepository;
import com.mallan.yujeongran.icebreaking.balance_game.repository.BalanceResultRepository;
import com.mallan.yujeongran.icebreaking.balance_game.repository.BalanceRoomRepository;
import com.mallan.yujeongran.icebreaking.review.enums.GameType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
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
    private final BalanceFinalResultRepository balanceFinalResultRepository;
    private final BalancePlayerService balancePlayerService;
    private final RedisTemplate<String, String> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final ManagementInfoService managementInfoService;

    public void startGame(String roomCode, BalanceStartGameRequestDto requestDto) {
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

        managementInfoService.incrementGameCount(GameType.BALANCE_GAME);

        stringRedisTemplate.opsForValue().set("room:" + roomCode + ":currentQuestionIdx", "0");

    }

    public BalanceCreateIngameQuestionResponseDto createIngameQuestion(String roomCode, BalanceCreateIngameQuestionRequestDto request) {
        if (!balancePlayerService.isHost(roomCode, request.getPlayerId())) {
            throw new IllegalArgumentException("호스트만 문제를 생성할 수 있습니다.");
        }

        String listKey = "room:" + roomCode + ":questionIds";
        String idxKey = "room:" + roomCode + ":currentQuestionIdx";

        String idxStr = stringRedisTemplate.opsForValue().get(idxKey);
        int idx = (idxStr == null) ? 0 : Integer.parseInt(idxStr);

        List<String> questionIds = stringRedisTemplate.opsForList().range(listKey, 0, -1);
        if (questionIds == null || idx >= questionIds.size()) {
            return null;
        }

        Long questionId = Long.parseLong(questionIds.get(idx));
        BalanceQuestion question = balanceQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("문제가 존재하지 않습니다."));

        stringRedisTemplate.opsForValue().set(idxKey, String.valueOf(idx + 1));

        return BalanceCreateIngameQuestionResponseDto.builder()
                .questionId(question.getId())
                .content(question.getContent())
                .choiceA(question.getChoiceA())
                .choiceB(question.getChoiceB())
                .build();
    }


    public void submitAnswer(String roomCode, Long questionId, BalanceSubmitAnswerRequestDto request) {
        String redisKey = "room:" + roomCode + ":question:" + questionId + ":result";
        String choice = request.getSelectedChoice();

        if (!choice.equals("A") && !choice.equals("B")) {
            throw new IllegalArgumentException("선택지는 A 또는 B만 가능합니다.");
        }

        String submitKey = "room:" + roomCode + ":question:" + questionId + ":submitted:" + request.getPlayerId();
        Boolean alreadySubmitted = stringRedisTemplate.hasKey(submitKey);
        if (alreadySubmitted) {
            throw new IllegalArgumentException("이미 해당 질문에 응답하였습니다.");
        }

        stringRedisTemplate.opsForHash().increment(redisKey, choice, 1);

        stringRedisTemplate.opsForValue().set(submitKey, "true", Duration.ofMinutes(10));

        stringRedisTemplate.opsForValue().set(
                "room:" + roomCode + ":question:" + questionId + ":answer:" + request.getPlayerId(),
                choice
        );

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

            BalanceQuestionResult result = BalanceQuestionResult.builder()
                    .room(room)
                    .question(question)
                    .countChoiceA(countA)
                    .countChoiceB(countB)
                    .build();

            balanceResultRepository.save(result);
        }
    }

    public List<BalanceQuestionResultResponseDto> getFinalResult(String roomCode) {
        BalanceRoom room = balanceRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("방이 존재하지 않습니다."));

        List<BalanceQuestionResult> results = balanceResultRepository.findByRoom(room);

        return results.stream()
                .map(result -> BalanceQuestionResultResponseDto.builder()
                        .questionId(result.getQuestion().getId())
                        .content(result.getQuestion().getContent())
                        .countA(result.getCountChoiceA())
                        .countB(result.getCountChoiceB())
                        .build())
                .collect(Collectors.toList());
    }

    public void restartGame(String roomCode, String playerId) {
        if (!balancePlayerService.isHost(roomCode, playerId)) {
            throw new IllegalArgumentException("호스트만 게임을 재시작할 수 있습니다.");
        }

        BalanceRoom room = balanceRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다."));

        String questionIdsKey = "room:" + roomCode + ":questionIds";
        String currentIdxKey = "room:" + roomCode + ":currentQuestionIdx";

        List<String> questionIds = stringRedisTemplate.opsForList().range(questionIdsKey, 0, -1);
        if (questionIds != null) {
            for (String qid : questionIds) {
                stringRedisTemplate.delete("room:" + roomCode + ":question:" + qid + ":result");

                Set<String> submittedKeys = stringRedisTemplate.keys("room:" + roomCode + ":question:" + qid + ":submitted:*");
                if (submittedKeys != null) {
                    stringRedisTemplate.delete(submittedKeys);
                }

                Set<String> answerKeys = stringRedisTemplate.keys("room:" + roomCode + ":question:" + qid + ":answer:*");
                if (answerKeys != null) {
                    stringRedisTemplate.delete(answerKeys);
                }
            }
        }

        stringRedisTemplate.delete(questionIdsKey);
        stringRedisTemplate.delete(currentIdxKey);

        List<BalanceQuestionResult> oldResults = balanceResultRepository.findByRoom(room);
        balanceResultRepository.deleteAll(oldResults);

        room.setTopicId(null);
        room.setQuestionCount(5);
    }

    public List<BalanceFinalResultResponseDto> calculateSimilarity(String roomCode, String targetPlayerId) {
        List<String> playerIds = redisTemplate.opsForList().range("room:" + roomCode + ":players", 0, -1);
        List<String> questionIds = stringRedisTemplate.opsForList().range("room:" + roomCode + ":questionIds", 0, -1);

        if (playerIds == null || questionIds == null) throw new IllegalStateException("데이터 없음");

        Map<Long, String> targetAnswers = new HashMap<>();

        for (String qid : questionIds) {
            String key = "room:" + roomCode + ":question:" + qid + ":submitted:" + targetPlayerId;
            if (stringRedisTemplate.hasKey(key)) {
                String selected = stringRedisTemplate.opsForValue().get("room:" + roomCode + ":question:" + qid + ":answer:" + targetPlayerId);
                targetAnswers.put(Long.parseLong(qid), selected);
            }
        }

        List<BalanceFinalResultResponseDto> result = new ArrayList<>();

        for (String otherPlayerId : playerIds) {
            if (otherPlayerId.equals(targetPlayerId)) continue;

            int matchCount = 0;
            int totalCount = questionIds.size();

            for (String qid : questionIds) {
                Long questionId = Long.parseLong(qid);
                String answerTarget = targetAnswers.get(questionId);
                String answerOther = stringRedisTemplate.opsForValue().get("room:" + roomCode + ":question:" + qid + ":answer:" + otherPlayerId);

                if (answerTarget != null && answerTarget.equals(answerOther)) {
                    matchCount++;
                }
            }

            int matchRate = (int) Math.round((matchCount * 100.0) / totalCount);
            result.add(BalanceFinalResultResponseDto.builder()
                    .playerId(otherPlayerId)
                    .nickname(balancePlayerService.getNickname(otherPlayerId))
                    .profileImage(balancePlayerService.getProfileImage(otherPlayerId))
                    .matchRate(matchRate)
                    .build());
        }

        return result;
    }

    public void deleteGame(String roomCode, String playerId) {
        if (!balancePlayerService.isHost(roomCode, playerId)) {
            throw new IllegalArgumentException("호스트만 게임을 끝낼 수 있습니다.");
        }

        BalanceRoom room = balanceRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("방이 존재하지 않습니다."));

        String questionIdsKey = "room:" + roomCode + ":questionIds";
        List<String> questionIds = stringRedisTemplate.opsForList().range(questionIdsKey, 0, -1);
        if (questionIds != null) {
            for (String qid : questionIds) {
                stringRedisTemplate.delete("room:" + roomCode + ":question:" + qid + ":result");

                Set<String> submittedKeys = stringRedisTemplate.keys("room:" + roomCode + ":question:" + qid + ":submitted:*");
                if (submittedKeys != null && !submittedKeys.isEmpty()) {
                    stringRedisTemplate.delete(submittedKeys);
                }

                Set<String> answerKeys = stringRedisTemplate.keys("room:" + roomCode + ":question:" + qid + ":answer:*");
                if (answerKeys != null && !answerKeys.isEmpty()) {
                    stringRedisTemplate.delete(answerKeys);
                }
            }
        }
        stringRedisTemplate.delete(questionIdsKey);
        stringRedisTemplate.delete("room:" + roomCode + ":currentQuestionIdx");

        balanceResultRepository.deleteAll(balanceResultRepository.findByRoom(room));
        balanceFinalResultRepository.deleteAll(balanceFinalResultRepository.findByRoom(room));

        balancePlayerService.deleteRoom(roomCode);

        balanceRoomRepository.delete(room);
    }

}
