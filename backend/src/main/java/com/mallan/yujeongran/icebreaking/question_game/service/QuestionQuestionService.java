package com.mallan.yujeongran.icebreaking.question_game.service;

import com.mallan.yujeongran.icebreaking.question_game.dto.request.QuestionCreateQuestionRequestDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.request.QuestionDeleteQuestionRequestDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.request.QuestionGetQuestionByTopicRequestDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.response.QuestionCreateQuestionResponseDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.response.QuestionDeleteQuestionResponseDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.response.QuestionQuestionResponseDto;
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
public class QuestionQuestionService {

    private final QuestionTopicRepository questionTopicRepository;
    private final QuestionQuestionRepository questionQuestionRepository;

    public QuestionCreateQuestionResponseDto createQuestion(QuestionCreateQuestionRequestDto request) {
        QuestionTopic topic = questionTopicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new IllegalArgumentException("해당 주제를 찾을 수 없습니다."));

        QuestionQuestion question = QuestionQuestion.builder()
                .topic(topic)
                .content(request.getContent())
                .build();

        QuestionQuestion saved = questionQuestionRepository.save(question);

        return QuestionCreateQuestionResponseDto.builder()
                .questionId(saved.getId())
                .topicId(topic.getId())
                .topic(topic.getTopic())
                .content(saved.getContent())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    public List<QuestionQuestionResponseDto> getQuestionByTopicId(QuestionGetQuestionByTopicRequestDto request) {
        List<QuestionQuestion> questions = questionQuestionRepository.findByTopicId(request.getTopicId());
        return questions.stream()
                .map(question -> QuestionQuestionResponseDto.builder()
                        .questionId(question.getId())
                        .topicId(question.getTopic().getId())
                        .topic(question.getTopic().getTopic())
                        .content(question.getContent())
                        .createdAt(question.getCreatedAt())
                        .build())
                .toList();
    }

    public QuestionDeleteQuestionResponseDto deleteQuestion(QuestionDeleteQuestionRequestDto request) {
        QuestionQuestion question = questionQuestionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 없습니다."));

        questionQuestionRepository.delete(question);

        return QuestionDeleteQuestionResponseDto.builder()
                .questionId(question.getId())
                .topicId(String.valueOf(question.getTopic().getId()))
                .topic(question.getTopic())
                .content(question.getContent())
                .build();
    }
}
