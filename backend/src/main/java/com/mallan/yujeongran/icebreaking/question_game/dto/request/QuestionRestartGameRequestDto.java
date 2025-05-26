package com.mallan.yujeongran.icebreaking.question_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class QuestionRestartGameRequestDto {

    @Schema(description = "플레이어 아이디", example = "l1m24ij5")
    private String playerId;

}
