package com.mallan.yujeongran.icebreaking.coin_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoinFinalResultResponseDto {

    private String playerId;
    private String nickname;
    private String profileImage;
    private int score;

}
