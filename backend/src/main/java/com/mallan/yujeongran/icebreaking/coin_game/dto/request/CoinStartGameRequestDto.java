package com.mallan.yujeongran.icebreaking.coin_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CoinStartGameRequestDto {

    @Schema(description = "플레이어 아이디", example = "lk3n251p")
    private String playerId;

}
