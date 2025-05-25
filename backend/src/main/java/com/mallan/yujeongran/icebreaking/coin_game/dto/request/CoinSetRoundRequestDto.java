package com.mallan.yujeongran.icebreaking.coin_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CoinSetRoundRequestDto {

    @Schema(description = "플레이어 아이디", example = "n14k5ua0")
    private String playerId;

    @Schema(description = "게임 라운드", example = "3")
    private int roundCount;
}
