package com.mallan.yujeongran.icebreaking.question_game.service;

import com.mallan.yujeongran.icebreaking.question_game.dto.request.QuestionCreateTopicRequestDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.request.QuestionDeleteTopicRequestDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.response.QuestionCreateTopicResponseDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.response.QuestionDeleteTopicResponseDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.response.QuestionTopicResponseDto;
import com.mallan.yujeongran.icebreaking.question_game.entity.QuestionQuestion;
import com.mallan.yujeongran.icebreaking.question_game.entity.QuestionTopic;
import com.mallan.yujeongran.icebreaking.question_game.repository.QuestionQuestionRepository;
import com.mallan.yujeongran.icebreaking.question_game.repository.QuestionTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class QuestionTopicService {

    private final QuestionTopicRepository questionTopicRepository;
    private final QuestionQuestionRepository questionQuestionRepository;

    public QuestionCreateTopicResponseDto createTopic(QuestionCreateTopicRequestDto request) {
        QuestionTopic topic = QuestionTopic.builder()
                .topic(request.getTopic())
                .build();

        QuestionTopic saved = questionTopicRepository.save(topic);

        return QuestionCreateTopicResponseDto.builder()
                .topicId(saved.getId())
                .topic(saved.getTopic())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    public List<QuestionTopicResponseDto> getAllTopics() {
        return questionTopicRepository.findAllTopic();
    }

    public QuestionDeleteTopicResponseDto deleteTopic(QuestionDeleteTopicRequestDto request) {
        QuestionTopic topic = questionTopicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new IllegalArgumentException("해당 주제를 찾을 수 없습니다."));

        List<QuestionQuestion> questions = questionQuestionRepository.findByTopicId(request.getTopicId());
        questionQuestionRepository.deleteAll(questions);

        questionTopicRepository.delete(topic);

        return QuestionDeleteTopicResponseDto.builder()
                .topicId(topic.getId())
                .topic(topic.getTopic())
                .build();
    }
}
