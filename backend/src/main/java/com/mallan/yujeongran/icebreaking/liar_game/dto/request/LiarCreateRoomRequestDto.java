package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarCreateRoomRequestDto {

    @Schema(description = "hostId", example = "a1b2c3d4")
    private String hostId;

    @Schema(description = "hostNickname", example = "Noah")
    private String hostNickname;

}
