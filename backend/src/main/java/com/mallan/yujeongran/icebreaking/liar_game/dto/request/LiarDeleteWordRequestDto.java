package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarDeleteWordRequestDto {

    @Schema(description = "단어 아이디", example = "1")
    private Long wordId;

}
