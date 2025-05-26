package com.mallan.yujeongran.icebreaking.question_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionJoinRoomResponseDto {

    private String nickname;
    private String playerId;
    private String profileImage;

}
