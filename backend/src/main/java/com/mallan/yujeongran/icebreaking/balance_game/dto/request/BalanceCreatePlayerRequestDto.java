package com.mallan.yujeongran.icebreaking.balance_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class BalanceCreatePlayerRequestDto {

    @Schema(description = "닉네임", example = "Noah")
    private String nickname;

    @Schema(description = "프로필 이미지", example = "avatar_3_140x140.png")
    private String profileImage;

}
