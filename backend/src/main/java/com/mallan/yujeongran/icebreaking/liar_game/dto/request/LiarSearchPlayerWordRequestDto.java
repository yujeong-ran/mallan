package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarSearchPlayerWordRequestDto {

    @Schema(description = "플에이어 아이디", example = "a1b2c3d4")
    private String playerId;

}
