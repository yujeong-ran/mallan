package com.mallan.yujeongran.icebreaking.question_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class QuestionDrawResponseDto {

    private Long questionId;
    private String questionContent;

}
