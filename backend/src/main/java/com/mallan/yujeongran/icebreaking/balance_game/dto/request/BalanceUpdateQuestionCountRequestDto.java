package com.mallan.yujeongran.icebreaking.balance_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class BalanceUpdateQuestionCountRequestDto {

    @Schema(description = "플레이어 아이디", example = "12nmb34b")
    private String playerId;

    @Schema(description = "문항 수", example = "1")
    private int questionCount;

}
