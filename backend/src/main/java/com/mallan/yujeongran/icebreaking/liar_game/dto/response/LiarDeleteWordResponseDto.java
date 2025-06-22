package com.mallan.yujeongran.icebreaking.liar_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LiarDeleteWordResponseDto {

    private Long wordId;
    private String word;

}
