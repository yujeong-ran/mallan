package com.mallan.yujeongran.icebreaking.question_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class QuestionSetQuestionCountRequestDto {

    @Schema(description = "플레이어 아이디", example = "01ml37l1")
    private String playerId;

    @Schema(description = "문제 수", example = "6")
    private int questionCount;

}
