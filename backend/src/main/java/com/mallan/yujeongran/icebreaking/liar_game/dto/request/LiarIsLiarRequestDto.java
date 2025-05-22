package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarIsLiarRequestDto {

    @Schema(description = "플레이어 아이디", example = "2ny78wi1")
    private String playerId;

}
