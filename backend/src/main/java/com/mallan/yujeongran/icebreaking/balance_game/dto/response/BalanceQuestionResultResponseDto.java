package com.mallan.yujeongran.icebreaking.balance_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BalanceQuestionResultResponseDto {

    private Long questionId;
    private String content;
    private int countA;
    private int countB;

}
