package com.mallan.yujeongran.icebreaking.question_game.repository;

import com.mallan.yujeongran.icebreaking.question_game.entity.QuestionRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QuestionRoomRepository extends JpaRepository<QuestionRoom, Long> {

    Optional<QuestionRoom> findByRoomCode(String roomCode);
    List<QuestionRoom> findByCreatedAtBefore(LocalDateTime time);

}
