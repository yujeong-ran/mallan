package com.mallan.yujeongran.icebreaking.coin_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CoinSubmitAnswerRequestDto {

    @Schema(description = "플레이어 ID", example = "z9y8x7w6")
    private String playerId;

    @Schema(description = "답변 (YES/NO)", example = "YES")
    private String answer;

}
