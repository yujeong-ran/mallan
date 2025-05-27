package com.mallan.yujeongran.icebreaking.coin_game.scheduler;

import com.mallan.yujeongran.icebreaking.coin_game.entity.CoinRoom;
import com.mallan.yujeongran.icebreaking.coin_game.repository.CoinRoomRepository;
import com.mallan.yujeongran.icebreaking.coin_game.service.CoinGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CoinRoomCleanupScheduler {

    private final CoinRoomRepository coinRoomRepository;
    private final CoinGameService coinGameService;

    @Scheduled(fixedRate = 3600000)
    public void deleteExpiredRooms() {
        LocalDateTime expiredTime = LocalDateTime.now().minusHours(24);
        List<CoinRoom> expiredRooms = coinRoomRepository.findByCreatedAtBefore(expiredTime);

        for (CoinRoom room : expiredRooms) {
            String roomCode = room.getRoomCode();
            coinGameService.endGame(roomCode);
            System.out.println("코인 진실게임 룸 삭제됨: " + roomCode);
        }
    }

}
