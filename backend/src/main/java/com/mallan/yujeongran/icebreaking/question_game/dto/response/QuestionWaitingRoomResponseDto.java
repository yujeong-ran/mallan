package com.mallan.yujeongran.icebreaking.question_game.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuestionWaitingRoomResponseDto {

    private String roomCode;
    private String hostId;
    private String hostNickname;
    private String url;
    private List<QuestionPlayInfoResponseDto> players;
    private int questionCount;
    private int playerCount;

}
