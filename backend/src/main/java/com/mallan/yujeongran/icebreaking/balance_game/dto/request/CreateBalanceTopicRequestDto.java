package com.mallan.yujeongran.icebreaking.balance_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CreateBalanceTopicRequestDto {

    @Schema(description = "주제 이름", example = "여행")
    private String topicName;

}
