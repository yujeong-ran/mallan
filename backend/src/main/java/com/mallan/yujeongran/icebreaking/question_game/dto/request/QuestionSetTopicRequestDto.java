package com.mallan.yujeongran.icebreaking.question_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class QuestionSetTopicRequestDto {

    @Schema(description = "플레이어 아이디", example = "lm3512bh")
    private String playerId;

    @Schema(description = "주제 아이디", example = "3")
    private int topicId;

}
