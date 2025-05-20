package com.mallan.yujeongran.icebreaking.balance_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BalanceQuestionResponseDto {

    private Long id;
    private String content;
    private String choiceA;
    private String choiceB;

}
