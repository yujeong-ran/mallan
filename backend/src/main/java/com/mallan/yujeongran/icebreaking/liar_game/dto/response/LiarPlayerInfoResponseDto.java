package com.mallan.yujeongran.icebreaking.liar_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LiarPlayerInfoResponseDto {

    private String playerId;
    private String nickname;
    private String profileImage;

}
