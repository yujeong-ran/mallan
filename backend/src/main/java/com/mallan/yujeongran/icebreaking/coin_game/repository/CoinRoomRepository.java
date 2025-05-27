package com.mallan.yujeongran.icebreaking.coin_game.repository;

import com.mallan.yujeongran.icebreaking.coin_game.entity.CoinRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CoinRoomRepository extends JpaRepository<CoinRoom, Long> {

    Optional<CoinRoom> findByRoomCode(String roomCode);
    List<CoinRoom> findByCreatedAtBefore(LocalDateTime time);

}
