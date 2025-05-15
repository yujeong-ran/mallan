package com.mallan.yujeongran.icebreaking.liar_game.service;

import com.mallan.yujeongran.icebreaking.liar_game.dto.response.LiarTopicResponseDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.CreateLiarTopicRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.entity.LiarTopic;
import com.mallan.yujeongran.icebreaking.liar_game.repository.LiarTopicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LiarTopicService {

    private final LiarTopicRepository liartopicRepository;

    public LiarTopicResponseDto createTopic(CreateLiarTopicRequestDto requestDto) {
        LiarTopic topic = LiarTopic.builder()
                .name(requestDto.getName())
                .build();

        return LiarTopicResponseDto.fromEntity(liartopicRepository.save(topic));
    }

    public List<LiarTopicResponseDto> getAllTopics() {
        return liartopicRepository.findAll().stream()
                .map(LiarTopicResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
