package com.mallan.yujeongran.icebreaking.question_game.dto.response;

import com.mallan.yujeongran.icebreaking.question_game.entity.QuestionTopic;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionDeleteQuestionResponseDto {

    private Long questionId;
    private String topicId;
    private QuestionTopic topic;
    private String content;

}
