package com.mallan.yujeongran.icebreaking.question_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionSetTopicResponseDto {

    private int topicId;
    private String topic;

}
