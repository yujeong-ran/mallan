package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarEndGameRequestDto {

    @Schema(description = "플레이어 아이디", example = "n514ndl1")
    private String hostId;

}
