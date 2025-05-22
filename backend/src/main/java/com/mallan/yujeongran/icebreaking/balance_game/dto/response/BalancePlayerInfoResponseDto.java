package com.mallan.yujeongran.icebreaking.balance_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BalancePlayerInfoResponseDto {

    private String playerId;
    private String nickname;
    private String profileImage;

}
