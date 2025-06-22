package com.mallan.yujeongran.icebreaking.liar_game.service;

import com.mallan.yujeongran.icebreaking.liar_game.dto.request.LiarCreateTopicRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.LiarDeleteTopicRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.response.LiarDeleteTopicResponseDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.response.LiarTopicResponseDto;
import com.mallan.yujeongran.icebreaking.liar_game.entity.LiarTopic;
import com.mallan.yujeongran.icebreaking.liar_game.entity.LiarWord;
import com.mallan.yujeongran.icebreaking.liar_game.repository.LiarTopicRepository;
import com.mallan.yujeongran.icebreaking.liar_game.repository.LiarWordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LiarTopicService {

    private final LiarTopicRepository liarTopicRepository;
    private final LiarWordRepository liarWordRepository;

    public LiarTopicResponseDto createTopic(LiarCreateTopicRequestDto requestDto) {
        LiarTopic topic = LiarTopic.builder()
                .name(requestDto.getName())
                .build();

        return LiarTopicResponseDto.fromEntity(liarTopicRepository.save(topic));
    }

    public List<LiarTopicResponseDto> getAllTopics() {
        return liarTopicRepository.findAll().stream()
                .map(LiarTopicResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public LiarDeleteTopicResponseDto deleteTopic(LiarDeleteTopicRequestDto request) {
        LiarTopic topic = liarTopicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new IllegalArgumentException("해당 주제가 존재하지 않습니다."));

        List<LiarWord> words = liarWordRepository.findByTopicId(request.getTopicId());
        liarWordRepository.deleteAll(words);

        liarTopicRepository.delete(topic);

        return LiarDeleteTopicResponseDto.builder()
                .topicId(topic.getId())
                .topic(topic.getName())
                .build();

    }

}
