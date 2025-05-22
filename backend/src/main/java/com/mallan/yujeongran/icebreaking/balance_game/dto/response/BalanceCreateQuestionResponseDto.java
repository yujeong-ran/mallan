package com.mallan.yujeongran.icebreaking.balance_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BalanceCreateQuestionResponseDto {

    private Long topicId;
    private String content;
    private String choiceA;
    private String choiceB;

}
