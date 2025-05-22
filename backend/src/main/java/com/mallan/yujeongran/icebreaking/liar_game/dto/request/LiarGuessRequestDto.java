package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarGuessRequestDto {

    @Schema(description = "플레이어 아이디", example = "m52lqw59")
    private String playerId;

    @Schema(description = "추측되는 시민의 단어", example = "크로아상")
    private String word;

}
