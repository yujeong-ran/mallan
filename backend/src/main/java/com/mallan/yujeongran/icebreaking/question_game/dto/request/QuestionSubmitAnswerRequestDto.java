package com.mallan.yujeongran.icebreaking.question_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class QuestionSubmitAnswerRequestDto {

    @Schema(description = "플레이어 아이디", example = "1l2m54kj")
    private String playerId;

    @Schema(description = "질문 아아디", example = "1")
    private Long questionId;

}