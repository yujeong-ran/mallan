package com.mallan.yujeongran.icebreaking.balance_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BalanceDeleteTopicResponseDto {

    private Long topicId;
    private String topic;

}
