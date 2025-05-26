package com.mallan.yujeongran.icebreaking.question_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionCreateRoomResponseDto {

    private String roomCode;
    private String hostId;
    private String hostNickname;
    private String hostProfileImage;
    private String url;

}
