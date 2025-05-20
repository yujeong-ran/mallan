package com.mallan.yujeongran.icebreaking.balance_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdateBalanceTopicRequestDto {

    @Schema(description = "주제 아이디", example = "1")
    private Long topicId;

}
