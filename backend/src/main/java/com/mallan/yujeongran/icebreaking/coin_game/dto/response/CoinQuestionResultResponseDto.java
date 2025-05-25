package com.mallan.yujeongran.icebreaking.coin_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoinQuestionResultResponseDto {

    private int yesCount;
    private int noCount;
    private int expectedYesCount;
    private boolean isCorrect;
    private int scoreAwarded;

}