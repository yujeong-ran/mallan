package com.mallan.yujeongran.icebreaking.question_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class QuestionGetQuestionByTopicRequestDto {

    @Schema(description = "주제 ID", example = "1")
    private Long topicId;

}
