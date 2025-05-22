package com.mallan.yujeongran.icebreaking.balance_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BalanceCreateIngameQuestionResponseDto {

    private Long questionId;
    private String content;
    private String choiceA;
    private String choiceB;

}
