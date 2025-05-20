package com.mallan.yujeongran.icebreaking.balance_game.service;

import com.mallan.yujeongran.icebreaking.balance_game.dto.request.CreateBalanceQuestionRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceQuestionResponseDto;
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

    public BalanceQuestionResponseDto createQuestion(Long topicId, CreateBalanceQuestionRequestDto request) {
        BalanceTopic topic = balanceTopicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주제를 찾을 수 없습니다."));

        BalanceQuestion question = BalanceQuestion.builder()
                .content(request.getContent())
                .choiceA(request.getChoiceA())
                .choiceB(request.getChoiceB())
                .build();

        balanceQuestionRepository.save(question);

        return BalanceQuestionResponseDto.builder()
                .id(question.getId())
                .content(question.getContent())
                .choiceA(question.getChoiceA())
                .choiceB(question.getChoiceB())
                .build();
    }

    public List<BalanceQuestionResponseDto> getQuestionsByTopicId(Long topicId) {
        BalanceTopic topic = balanceTopicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주제를 찾을 수 없습니다."));

        List<BalanceQuestion> questions = balanceQuestionRepository.findByTopicId(topicId);

        return questions.stream()
                .map(q -> BalanceQuestionResponseDto.builder()
                        .id(q.getId())
                        .content(q.getContent())
                        .choiceA(q.getChoiceA())
                        .choiceB(q.getChoiceB())
                        .build())
                .toList();
    }

}
