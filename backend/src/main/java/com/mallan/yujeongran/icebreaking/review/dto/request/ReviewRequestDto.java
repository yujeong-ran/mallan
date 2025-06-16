package com.mallan.yujeongran.icebreaking.review.dto.request;

import com.mallan.yujeongran.icebreaking.review.enums.GameType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {

    @Schema(
            description = "게임 종류 (LIAR_GAME, BALANCE_GAME, OPEN_QUESTION_GAME, COIN_TRUTH_GAME)",
            example = "LIAR_GAME"
    )
    private GameType gameType;

    @Schema(description = "닉네임", example = "뜨거운감자")
    private String nickname;

    @Schema(description = "평점(1~5 정수)", example = "5")
    private int grade;

    @Schema(description = "리뷰 내용", example = "너무 재밌어요!")
    private String content;

}
