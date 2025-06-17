package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarWebSocketExitRoomRequestDto {

    @Schema(description = "방 코드")
    private String roomCode;

    @Schema(description = "플레이어 ID")
    private String playerId;

    public LiarExitRoomRequestDto toDto() {
        LiarExitRoomRequestDto dto = new LiarExitRoomRequestDto();
        dto.setPlayerId(playerId);
        return dto;
    }

}
