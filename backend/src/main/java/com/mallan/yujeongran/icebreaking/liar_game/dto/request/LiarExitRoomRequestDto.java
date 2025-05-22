package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarExitRoomRequestDto {

    @Schema(description = "playerId", example = "a1b2c3d4")
    private String playerId;

}
