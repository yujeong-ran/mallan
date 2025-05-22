package com.mallan.yujeongran.icebreaking.balance_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BalanceJoinRoomResponseDto {

    private String playerId;
    private String profileImage;

}
