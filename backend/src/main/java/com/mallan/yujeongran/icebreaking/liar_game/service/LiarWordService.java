package com.mallan.yujeongran.icebreaking.liar_game.service;

import com.mallan.yujeongran.icebreaking.liar_game.dto.request.LiarDeleteWordRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.response.LiarDeleteWordResponseDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.response.LiarWordResponseDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.LiarCreateWordRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.entity.LiarTopic;
import com.mallan.yujeongran.icebreaking.liar_game.entity.LiarWord;
import com.mallan.yujeongran.icebreaking.liar_game.repository.LiarTopicRepository;
import com.mallan.yujeongran.icebreaking.liar_game.repository.LiarWordRepository;
import com.mallan.yujeongran.common.exception.NotFoundTopicException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LiarWordService {

    private final LiarWordRepository liarWordRepository;
    private final LiarTopicRepository liarTopicRepository;

    public LiarWordResponseDto createWord(LiarCreateWordRequestDto requestDto) {
        LiarTopic topic = liarTopicRepository.findById(requestDto.getTopicId())
                .orElseThrow(NotFoundTopicException::new);

        LiarWord word = LiarWord.builder()
                .topic(topic)
                .word(requestDto.getWord())
                .build();

        return LiarWordResponseDto.fromEntity(liarWordRepository.save(word));
    }

    public List<LiarWordResponseDto> getWordsByTopic(Long topicId) {
        return liarWordRepository.findByTopicId(topicId).stream()
                .map(LiarWordResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public LiarDeleteWordResponseDto deleteWord(LiarDeleteWordRequestDto request){
        LiarWord word = liarWordRepository.findById(request.getWordId())
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 없습니다."));

        liarWordRepository.delete(word);

        return LiarDeleteWordResponseDto.builder()
                .wordId(word.getId())
                .word(word.getWord())
                .build();
    }

}
