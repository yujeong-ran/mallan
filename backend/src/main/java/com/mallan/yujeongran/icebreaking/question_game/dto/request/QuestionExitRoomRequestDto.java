package com.mallan.yujeongran.icebreaking.question_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class QuestionExitRoomRequestDto {

    @Schema(description = "플레이어 아이디", example = "kl2m41k2")
    private String playerId;

}
