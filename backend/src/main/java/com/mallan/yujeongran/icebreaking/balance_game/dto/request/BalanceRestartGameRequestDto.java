package com.mallan.yujeongran.icebreaking.balance_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class BalanceRestartGameRequestDto {

    @Schema(description = "플레이어 ID", example = "a1b2c3d4")
    private String playerId;

}
