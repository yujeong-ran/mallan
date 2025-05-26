package com.mallan.yujeongran.icebreaking.question_game.repository;

import com.mallan.yujeongran.icebreaking.question_game.entity.QuestionPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionPlayerRepository extends JpaRepository<QuestionPlayer, Long> {

    List<QuestionPlayer> findByRoomCode(String roomCode);

}
