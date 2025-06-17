package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarJoinRoomRequestDto {

    @Schema(description = "닉네임", example = "noah")
    private String nickname;

    @Schema(description = "프로필 이미지", example = "avatar_4_140x140.png")
    private String profileImage;

    public LiarJoinRoomRequestDto(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

}
