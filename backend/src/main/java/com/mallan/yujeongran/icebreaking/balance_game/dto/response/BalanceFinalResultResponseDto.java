package com.mallan.yujeongran.icebreaking.balance_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BalanceFinalResultResponseDto {

    private String playerId;
    private String nickname;
    private String profileImage;
    private int matchRate;
    private int matchCount;
    private int totalCount;

}
