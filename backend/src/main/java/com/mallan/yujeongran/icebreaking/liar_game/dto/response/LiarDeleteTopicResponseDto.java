package com.mallan.yujeongran.icebreaking.liar_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LiarDeleteTopicResponseDto {

    private Long topicId;
    private String topic;

}
