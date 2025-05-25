package com.mallan.yujeongran.icebreaking.coin_game.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CoinFinalResultListResponseDto {

    private List<CoinFinalResultResponseDto> results;

}
