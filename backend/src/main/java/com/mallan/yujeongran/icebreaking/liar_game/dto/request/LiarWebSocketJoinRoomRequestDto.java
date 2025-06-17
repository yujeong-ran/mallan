package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarWebSocketJoinRoomRequestDto {

    @Schema(description = "방 코드", example = "abcd1234")
    private String roomCode;

    @Schema(description = "닉네임", example = "noah")
    private String nickname;

    @Schema(description = "프로필 이미지", example = "avatar_4_140x140.png")
    private String profileImage;

}
