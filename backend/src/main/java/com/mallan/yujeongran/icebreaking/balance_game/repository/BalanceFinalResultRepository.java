package com.mallan.yujeongran.icebreaking.balance_game.repository;

import com.mallan.yujeongran.icebreaking.balance_game.entity.BalanceFinalResultEntity;
import com.mallan.yujeongran.icebreaking.balance_game.entity.BalanceRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceFinalResultRepository extends JpaRepository<BalanceFinalResultEntity, Long> {

    List<BalanceFinalResultEntity> findByRoom(BalanceRoom romm);

}
