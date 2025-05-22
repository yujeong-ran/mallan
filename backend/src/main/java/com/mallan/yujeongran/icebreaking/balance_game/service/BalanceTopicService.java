package com.mallan.yujeongran.icebreaking.balance_game.service;

import com.mallan.yujeongran.icebreaking.balance_game.dto.request.BalanceCreateTopicRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceTopicResponseDto;
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

}

