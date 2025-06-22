package com.mallan.yujeongran.icebreaking.question_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionDeleteTopicResponseDto {

    private Long topicId;
    private String topic;

}
