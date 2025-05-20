package com.mallan.yujeongran.icebreaking.balance_game.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BalanceFinalResultResponseDto {

    @Schema
    private Long questionId;

    @Schema
    private String content;

    @Schema
    private int countA;

    @Schema
    private int countB;

}
