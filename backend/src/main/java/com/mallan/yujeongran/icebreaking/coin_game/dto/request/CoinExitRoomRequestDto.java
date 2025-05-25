package com.mallan.yujeongran.icebreaking.coin_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CoinExitRoomRequestDto {

    @Schema(description = "플레이어 아이디", example = "m310lam5")
    private String playerId;

}
