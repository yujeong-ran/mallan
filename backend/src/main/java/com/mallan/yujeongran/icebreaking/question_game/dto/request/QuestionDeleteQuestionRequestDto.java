package com.mallan.yujeongran.icebreaking.question_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class QuestionDeleteQuestionRequestDto {

    @Schema(description = "질문 아이디", example = "1")
    private Long questionId;

}
