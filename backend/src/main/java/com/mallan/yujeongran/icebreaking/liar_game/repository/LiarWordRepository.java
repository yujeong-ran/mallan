package com.mallan.yujeongran.icebreaking.liar_game.repository;

import com.mallan.yujeongran.icebreaking.liar_game.entity.LiarWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LiarWordRepository extends JpaRepository<LiarWord, Long> {
    List<LiarWord> findByTopicId(Long topicId);
}
