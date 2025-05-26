package com.mallan.yujeongran.icebreaking.question_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class QuestionSelectAnswererRequestDto {

    @Schema(description = "질문자 아이디", example = "46lm12kj")
    private String questionerId;

    @Schema(description = "답변자 아이디", example = "67lk291p")
    private String answererId;

}