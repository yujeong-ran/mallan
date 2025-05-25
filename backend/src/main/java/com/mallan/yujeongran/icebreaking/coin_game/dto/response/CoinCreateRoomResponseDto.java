package com.mallan.yujeongran.icebreaking.coin_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoinCreateRoomResponseDto {

    private String roomCode;
    private String hostId;
    private String hostNickname;
    private String url;

}
