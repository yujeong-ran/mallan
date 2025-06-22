package com.mallan.yujeongran.icebreaking.question_game.repository;

import com.mallan.yujeongran.icebreaking.question_game.entity.QuestionQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionQuestionRepository extends JpaRepository<QuestionQuestion, Long> {

    List<QuestionQuestion> findByTopicId(Long topicId);


}