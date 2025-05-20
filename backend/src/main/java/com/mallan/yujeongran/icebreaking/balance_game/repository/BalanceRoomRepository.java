package com.mallan.yujeongran.icebreaking.balance_game.repository;

import com.mallan.yujeongran.icebreaking.balance_game.entity.BalanceRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRoomRepository extends JpaRepository<BalanceRoom, Long> {
    Optional<BalanceRoom> findByRoomCode(String roomCode);
}
