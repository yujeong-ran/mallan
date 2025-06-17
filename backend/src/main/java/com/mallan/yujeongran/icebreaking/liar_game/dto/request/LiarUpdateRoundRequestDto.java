package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarUpdateRoundRequestDto {

    @Schema(description = "플레이어 아이디", example = "1skl91ma")
    private String hostId;

    @Schema(description = "게임 라운드", example = "3")
    private int round;

}

