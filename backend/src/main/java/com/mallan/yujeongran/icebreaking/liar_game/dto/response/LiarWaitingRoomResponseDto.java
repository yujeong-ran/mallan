package com.mallan.yujeongran.icebreaking.liar_game.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LiarWaitingRoomResponseDto {

    private String roomCode;
    private String playerId;
    private int playerCount;
    private int descriptionCount;
    private List<LiarPlayerInfoResponseDto> players;

}
