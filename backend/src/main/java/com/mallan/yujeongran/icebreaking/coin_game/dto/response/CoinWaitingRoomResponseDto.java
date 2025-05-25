package com.mallan.yujeongran.icebreaking.coin_game.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CoinWaitingRoomResponseDto {

    private String roomCode;
    private String hostId;
    private String hostNickname;
    private int roundCount;
    private List<CoinWaitingPlayerResponseDto> players;

}
