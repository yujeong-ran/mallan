package com.mallan.yujeongran.icebreaking.balance_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class BalanceCreateIngameQuestionRequestDto {

    @Schema(description = "플레이어 아이디", example = "ba231mq7")
    private String playerId;

}
