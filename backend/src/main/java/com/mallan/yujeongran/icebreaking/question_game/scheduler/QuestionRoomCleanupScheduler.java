package com.mallan.yujeongran.icebreaking.question_game.scheduler;

import com.mallan.yujeongran.icebreaking.question_game.entity.QuestionRoom;
import com.mallan.yujeongran.icebreaking.question_game.repository.QuestionRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionRoomCleanupScheduler {

    private final QuestionRoomRepository questionRoomRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void deleteExpiredRooms() {
        LocalDateTime now = LocalDateTime.now().minusHours(24);
        List<QuestionRoom> expiredRooms = questionRoomRepository.findByCreatedAtBefore(now);

        for (QuestionRoom room : expiredRooms) {
            String roomCode = room.getRoomCode();

            redisTemplate.delete("question:room:" + roomCode);
            redisTemplate.delete("question:room:" + roomCode + ":players");
            redisTemplate.delete("question:room:" + roomCode + ":questions");
            redisTemplate.delete("question:room:" + roomCode + ":currentQuestionId");
            redisTemplate.delete("question:room:" + roomCode + ":questionerId");
            redisTemplate.delete("question:room:" + roomCode + ":answererId");
            redisTemplate.delete("question:room:" + roomCode + ":nextQuestionerId");

            List<String> playerIds = redisTemplate.opsForList().range("question:room:" + roomCode + ":players", 0, -1);
            if (playerIds != null) {
                for (String playerId : playerIds) {
                    redisTemplate.delete("question:player:" + playerId + ":nickname");
                    redisTemplate.delete("question:player:" + playerId + ":profileImage");
                }
            }

            questionRoomRepository.delete(room);
        }
    }
}
