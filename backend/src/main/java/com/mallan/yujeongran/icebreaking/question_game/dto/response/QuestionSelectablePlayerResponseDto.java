package com.mallan.yujeongran.icebreaking.question_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionSelectablePlayerResponseDto {

    private String playerId;
    private String nickname;
    private String profileImage;

}
