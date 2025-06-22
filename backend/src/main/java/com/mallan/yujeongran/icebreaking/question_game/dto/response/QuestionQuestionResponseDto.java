package com.mallan.yujeongran.icebreaking.question_game.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class QuestionQuestionResponseDto {

    private Long questionId;
    private Long topicId;
    private String topic;
    private String content;
    private LocalDateTime createdAt;

}
