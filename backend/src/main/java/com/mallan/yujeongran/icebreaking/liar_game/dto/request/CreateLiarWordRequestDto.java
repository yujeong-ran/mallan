package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CreateLiarWordRequestDto {

    @Schema(description = "topicId ", example = "1")
    private Long topicId;

    @Schema(description = "word", example = "피자")
    private String word;

}
