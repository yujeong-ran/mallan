package com.mallan.yujeongran.icebreaking.balance_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SubmitBalanceAnswerRequestDto {

    @Schema(description = "플레이어 아이디", example = "a1b2c3d4")
    private String playerId;

    @Schema(description = "문제 아이디", example = "1")
    private Long questionId;

    @Schema(description = "선택한 답변", example = "A")
    private String selectedChoice;

}
