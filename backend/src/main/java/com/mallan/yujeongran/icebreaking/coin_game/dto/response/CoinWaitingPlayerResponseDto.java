package com.mallan.yujeongran.icebreaking.coin_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoinWaitingPlayerResponseDto {

    private String playerId;
    private String nickname;
    private String profileImage;

}
