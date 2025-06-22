package com.mallan.yujeongran.icebreaking.question_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class QuestionCreateTopicRequestDto {

    @Schema(description = "주제", example = "가치관")
    private String topic;

}
