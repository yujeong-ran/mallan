package com.mallan.yujeongran.icebreaking.question_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class QuestionGameStartRequestDto {

    @Schema(description = "플레이어 아이디", example = "34lk5m10")
    private String playerId;

}
