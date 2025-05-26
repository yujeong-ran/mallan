package com.mallan.yujeongran.icebreaking.question_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionCurrentQuestionStateResponseDto {

    private String questionerId;
    private String questionerNickname;
    private Long questionId;
    private String questionContent;
    private String answererId;
    private String answererNickname;

}
