package com.mallan.yujeongran.icebreaking.coin_game.repository;

import com.mallan.yujeongran.icebreaking.coin_game.entity.CoinAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoinAnswerRepository extends JpaRepository<CoinAnswer, Long> {

    List<CoinAnswer> findByQuestionId(Long questionId);
}
