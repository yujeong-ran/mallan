package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarHostVerifyRequestDto {

    @Schema(description = "플레이어 아이디", example = "a1b2c4d4")
    private String playerId;

}
