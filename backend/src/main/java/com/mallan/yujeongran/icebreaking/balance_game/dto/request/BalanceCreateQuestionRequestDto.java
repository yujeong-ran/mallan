package com.mallan.yujeongran.icebreaking.balance_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class BalanceCreateQuestionRequestDto {

    @Schema(description = "주제 아이디", example = "1")
    private Long topicId;

    @Schema(description = "질문 내용", example = "바다 vs 산?")
    private String content;

    @Schema(description = "선택지 A", example = "바다")
    private String choiceA;

    @Schema(description = "선택지 B", example = "산")
    private String choiceB;


}
