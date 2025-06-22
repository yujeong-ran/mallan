package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarDeleteTopicRequestDto {

    @Schema(description = "토픽 아아디", example = "1")
    private Long topicId;

}
