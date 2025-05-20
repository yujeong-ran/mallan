package com.mallan.yujeongran.icebreaking.balance_game.repository;

import com.mallan.yujeongran.icebreaking.balance_game.entity.BalanceQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BalanceQuestionRepository extends JpaRepository<BalanceQuestion, Long> {

    @Query("SELECT q FROM BalanceQuestion q WHERE q.topic.id = :topicId")
    List<BalanceQuestion> findByTopicId(@Param("topicId") Long topicId);

}

