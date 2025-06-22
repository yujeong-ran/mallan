package com.mallan.yujeongran.icebreaking.balance_game.service;

import com.mallan.yujeongran.icebreaking.balance_game.dto.request.BalanceCreateTopicRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.BalanceDeleteTopicRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceDeleteTopicResponseDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceTopicResponseDto;
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
public class BalanceTopicService {

    private final BalanceTopicRepository balanceTopicRepository;
    private final BalanceQuestionRepository balanceQuestionRepository;

    public BalanceTopicResponseDto createTopic(BalanceCreateTopicRequestDto request) {
        BalanceTopic topic = BalanceTopic.builder()
                .topicName(request.getTopicName())
                .build();

        balanceTopicRepository.save(topic);

        return BalanceTopicResponseDto.builder()
                .id(topic.getId())
                .topicName(topic.getTopicName())
                .build();
    }

    public List<BalanceTopicResponseDto> getAllTopics() {
        return balanceTopicRepository.findAll().stream()
                .map(topic -> BalanceTopicResponseDto.builder()
                        .id(topic.getId())
                        .topicName(topic.getTopicName())
                        .build())
                .toList();
    }

    public BalanceDeleteTopicResponseDto deleteTopic(BalanceDeleteTopicRequestDto request) {
        BalanceTopic topic = balanceTopicRepository.findById(request.getTopicId())
                .orElseThrow(() ->  new IllegalArgumentException("해당 단어를 찾을 수 없습니다."));

        List<BalanceQuestion> questions = balanceQuestionRepository.findByTopicId(request.getTopicId());
        balanceQuestionRepository.deleteAll(questions);

        balanceTopicRepository.delete(topic);

        return BalanceDeleteTopicResponseDto.builder()
                .topicId(topic.getId())
                .topic(topic.getTopicName())
                .build();
    }

}

