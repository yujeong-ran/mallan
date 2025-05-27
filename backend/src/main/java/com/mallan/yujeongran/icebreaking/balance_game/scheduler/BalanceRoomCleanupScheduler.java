package com.mallan.yujeongran.icebreaking.balance_game.scheduler;

import com.mallan.yujeongran.icebreaking.balance_game.entity.BalanceRoom;
import com.mallan.yujeongran.icebreaking.balance_game.repository.BalanceRoomRepository;
import com.mallan.yujeongran.icebreaking.balance_game.service.BalancePlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class BalanceRoomCleanupScheduler {

    private final BalanceRoomRepository balanceRoomRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final BalancePlayerService balancePlayerService;

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void deleteExpiredRooms() {
        LocalDateTime expiredTime = LocalDateTime.now().minusHours(24);
        List<BalanceRoom> expiredRooms = balanceRoomRepository.findAll().stream()
                .filter(room -> room.getCreatedAt().isBefore(expiredTime))
                .toList();

        for (BalanceRoom room : expiredRooms) {
            String roomCode = room.getRoomCode();

            stringRedisTemplate.delete("room:" + roomCode);
            stringRedisTemplate.delete("room:" + roomCode + ":players");
            stringRedisTemplate.delete("room:" + roomCode + ":questionIds");
            stringRedisTemplate.delete("room:" + roomCode + ":currentQuestionIdx");

            List<String> questionIds = stringRedisTemplate.opsForList().range("room:" + roomCode + ":questionIds", 0, -1);
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

            List<String> playerIds = stringRedisTemplate.opsForList().range("room:" + roomCode + ":players", 0, -1);
            if (playerIds != null) {
                for (String playerId : playerIds) {
                    stringRedisTemplate.delete("player:" + playerId + ":nickname");
                    stringRedisTemplate.delete("player:" + playerId + ":profileImage");
                }
            }

            balancePlayerService.deleteRoom(roomCode);
            balanceRoomRepository.delete(room);

            System.out.println("삭제된 밸런스 게임 방: " + roomCode);
        }
    }

}
