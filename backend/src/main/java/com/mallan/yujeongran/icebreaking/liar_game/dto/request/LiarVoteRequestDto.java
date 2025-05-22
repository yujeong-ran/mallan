package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarVoteRequestDto {

    @Schema(description = "플레이어 아이디", example = "l52aiw49")
    private String playerId;

    @Schema(description = "투표 할 대상 플레이어 아이디", example = "n53nl15k")
    private String targetId;

}

