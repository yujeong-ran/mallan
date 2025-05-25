package com.mallan.yujeongran.icebreaking.coin_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CoinJoinRoomRequestDto {

    @Schema(description = "닉네임", example = "guest_1")
    private String nickname;

    @Schema(description = "프로필 이미지", example = "avatar_2_140x140.png")
    private String profileImage;

}
