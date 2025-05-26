package com.mallan.yujeongran.icebreaking.question_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionExitRoomResponseDto {

    private String roomCode;
    private String playerId;
    private String nickname;

}
