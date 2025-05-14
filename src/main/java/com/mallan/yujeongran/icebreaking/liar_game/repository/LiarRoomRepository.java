package com.mallan.yujeongran.icebreaking.liar_game.repository;

import com.mallan.yujeongran.icebreaking.liar_game.entity.LiarRoom;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LiarRoomRepository extends JpaRepository<LiarRoom, Long> {

    Optional<LiarRoom> findByRoomCode(String roomCode);
    List<LiarRoom> findByCreatedAtBefore(LocalDateTime cutoff);

}
