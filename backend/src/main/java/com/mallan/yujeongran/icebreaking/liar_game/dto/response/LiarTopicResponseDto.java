package com.mallan.yujeongran.icebreaking.liar_game.dto.response;

import com.mallan.yujeongran.icebreaking.liar_game.entity.LiarTopic;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LiarTopicResponseDto {

    private final Long id;
    private final String name;

    public LiarTopicResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static LiarTopicResponseDto fromEntity(LiarTopic topic) {
        return LiarTopicResponseDto.builder()
                .id(topic.getId())
                .name(topic.getName())
                .build();
    }

}
