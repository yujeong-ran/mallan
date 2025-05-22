package com.mallan.yujeongran.icebreaking.balance_game.repository;

import com.mallan.yujeongran.icebreaking.balance_game.entity.BalanceQuestionResult;
import com.mallan.yujeongran.icebreaking.balance_game.entity.BalanceRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceResultRepository extends JpaRepository<BalanceQuestionResult, Long> {

    List<BalanceQuestionResult> findByRoom(BalanceRoom room);

}

