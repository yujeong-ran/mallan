package com.mallan.yujeongran.icebreaking.liar_game.scheduler;

import com.mallan.yujeongran.icebreaking.liar_game.entity.LiarRoom;
import com.mallan.yujeongran.icebreaking.liar_game.repository.LiarRoomRepository;
import com.mallan.yujeongran.icebreaking.liar_game.service.LiarRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LiarRoomCleanupScheduler {

    private final LiarRoomRepository liarRoomRepository;
    private final LiarRedisService liarRedisService;

    @Scheduled(fixedRate = 3600000)
    public void deleteExpiredRooms() {
        LocalDateTime now = LocalDateTime.now().minusHours(24);
        List<LiarRoom> expiredRooms = liarRoomRepository.findByCreatedAtBefore(now);

        for (LiarRoom room : expiredRooms) {
            liarRoomRepository.delete(room);
            liarRedisService.deleteRoom(room.getRoomCode());
        }
    }

}
