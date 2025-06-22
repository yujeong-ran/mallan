package com.mallan.yujeongran.icebreaking.balance_game.service;

import com.mallan.yujeongran.icebreaking.balance_game.dto.request.BalanceCreateQuestionRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.BalanceDeleteQuestionRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceCreateQuestionResponseDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceDeleteQuestionResponseDto;
import com.mallan.yujeongran.icebreaking.balance_game.entity.BalanceQuestion;
import com.mallan.yujeongran.icebreaking.balance_game.entity.BalanceTopic;
import com.mallan.yujeongran.icebreaking.balance_game.repository.BalanceQuestionRepository;
import com.mallan.yujeongran.icebreaking.balance_game.repository.BalanceTopicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BalanceQuestionService {

    private final BalanceTopicRepository balanceTopicRepository;
    private final BalanceQuestionRepository balanceQuestionRepository;

    public BalanceCreateQuestionResponseDto createQuestion(Long topicId, BalanceCreateQuestionRequestDto request) {
        BalanceTopic topic = balanceTopicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주제를 찾을 수 없습니다."));

        BalanceQuestion question = BalanceQuestion.builder()
                .content(request.getContent())
                .choiceA(request.getChoiceA())
                .choiceB(request.getChoiceB())
                .build();

        balanceQuestionRepository.save(question);

        return BalanceCreateQuestionResponseDto.builder()
                .topicId(question.getId())
                .content(question.getContent())
                .choiceA(question.getChoiceA())
                .choiceB(question.getChoiceB())
                .build();
    }

    public List<BalanceCreateQuestionResponseDto> getQuestionsByTopicId(Long topicId) {
        BalanceTopic topic = balanceTopicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주제를 찾을 수 없습니다."));

        List<BalanceQuestion> questions = balanceQuestionRepository.findByTopicId(topicId);

        return questions.stream()
                .map(q -> BalanceCreateQuestionResponseDto.builder()
                        .topicId(q.getId())
                        .content(q.getContent())
                        .choiceA(q.getChoiceA())
                        .choiceB(q.getChoiceB())
                        .build())
                .toList();
    }

    public BalanceDeleteQuestionResponseDto deleteQuestion(BalanceDeleteQuestionRequestDto request){
        BalanceQuestion question = balanceQuestionRepository.findById((request.getQuestionId()))
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 없습니다."));

        balanceQuestionRepository.delete(question);

        return BalanceDeleteQuestionResponseDto.builder()
                .questionId(question.getId())
                .question(question.getContent())
                .build();

    }

}
