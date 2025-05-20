package com.mallan.yujeongran.icebreaking.balance_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BalanceRoomResponseDto {

    private String playerId;
    private String roomCode;
    private String url;

}
