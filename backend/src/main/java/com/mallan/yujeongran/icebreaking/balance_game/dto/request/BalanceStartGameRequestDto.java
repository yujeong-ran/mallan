package com.mallan.yujeongran.icebreaking.balance_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class BalanceStartGameRequestDto {

    @Schema(description = "플레이어 아이디", example = "abc123ef")
    private String playerId;

}
