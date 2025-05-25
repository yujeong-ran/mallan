package com.mallan.yujeongran.icebreaking.coin_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CoinSubmitQuestionRequestDto {

    @Schema(description = "플레이어 ID", example = "a1b2c3d4")
    private String playerId;

    @Schema(description = "질문 내용", example = "나는 오늘 아침을 먹었다!")
    private String question;

    @Schema(description = "예상 YES 인원 수", example = "2")
    private int expectedYesCount;

}
