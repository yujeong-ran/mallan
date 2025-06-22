package com.mallan.yujeongran.icebreaking.question_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class QuestionCreateQuestionRequestDto {

    @Schema(description = "주제 아이디", example = "1")
    private Long topicId;

    @Schema(description = "질문 내용", example = "일주일 동안 여행을 한다면 가장 가고싶은 나라는?")
    private String content;

}
