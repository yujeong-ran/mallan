package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarCanStartRequestDto {

    @Schema(description = "플레이어 아이디", example = "m4l18at6")
    private String playerId;

}
