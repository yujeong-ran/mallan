package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarWebSocketUpdateRoundRequestDto {

    @Schema(description = "방 코드", example = "abc123ef")
    private String roomCode;

    @Schema(description = "호스트 아이디", example = "1skl91ma")
    private String hostId;

    @Schema(description = "설정할 라운드 수", example = "3")
    private int round;

}
