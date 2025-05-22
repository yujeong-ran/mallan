package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarJoinRoomRequestDto {

    @Schema(description = "playerId", example = "a1b2c3d4")
    private String playerId;

    @Schema(description = "nickname", example = "noah")
    private String nickname;

}
