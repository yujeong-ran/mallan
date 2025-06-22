package com.mallan.yujeongran.icebreaking.question_game.repository;

import com.mallan.yujeongran.icebreaking.question_game.dto.response.QuestionTopicResponseDto;
import com.mallan.yujeongran.icebreaking.question_game.entity.QuestionTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionTopicRepository extends JpaRepository<QuestionTopic, Long> {

    @Query("SELECT new com.mallan.yujeongran.icebreaking.question_game.dto.response.QuestionTopicResponseDto(t.id, t.topic, t.createdAt) FROM QuestionTopic t")
    List<QuestionTopicResponseDto> findAllTopic();

}
