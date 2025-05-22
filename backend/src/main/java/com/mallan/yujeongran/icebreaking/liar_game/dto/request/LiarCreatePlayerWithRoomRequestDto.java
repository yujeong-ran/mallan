package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarCreatePlayerWithRoomRequestDto {

    @Schema(description = "nickname", example = "Noah")
    private String nickname;

    @Schema(description = "profileImage", example = "avatar_2_140x140.png")
    private String profileImage;

}
