package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarCreateTopicRequestDto {

    @Schema(description = "name", example = "음식")
    private String name;

}
