package com.mallan.yujeongran.icebreaking.balance_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BalanceDeleteQuestionResponseDto {

    private Long questionId;
    private String question;

}
