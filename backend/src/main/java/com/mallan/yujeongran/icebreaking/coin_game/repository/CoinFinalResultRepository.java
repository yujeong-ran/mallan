package com.mallan.yujeongran.icebreaking.coin_game.repository;

import com.mallan.yujeongran.icebreaking.coin_game.entity.CoinFinalResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoinFinalResultRepository extends JpaRepository<CoinFinalResult, Long> {

    List<CoinFinalResult> findByRoomCodeOrderByScoreDesc(String roomCode);

}
