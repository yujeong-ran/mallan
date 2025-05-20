package com.mallan.yujeongran.icebreaking.balance_game.repository;

import com.mallan.yujeongran.icebreaking.balance_game.entity.BalancePlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalancePlayerRepository extends JpaRepository<BalancePlayer, Long> {
}
