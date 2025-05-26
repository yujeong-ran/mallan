package com.mallan.yujeongran.icebreaking.question_game.repository;

import com.mallan.yujeongran.icebreaking.question_game.entity.QuestionQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionQuestionRepository extends JpaRepository<QuestionQuestion, Long> {

    List<QuestionQuestion> findByTopicId(int topicId);

}