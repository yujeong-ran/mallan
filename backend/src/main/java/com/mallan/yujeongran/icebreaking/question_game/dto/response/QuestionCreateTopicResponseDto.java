package com.mallan.yujeongran.icebreaking.question_game.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class QuestionCreateTopicResponseDto {

    private Long topicId;
    private String topic;
    private LocalDateTime createdAt;

}
