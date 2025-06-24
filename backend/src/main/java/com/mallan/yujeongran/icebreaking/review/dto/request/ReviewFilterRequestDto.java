package com.mallan.yujeongran.icebreaking.review.dto.request;

import com.mallan.yujeongran.icebreaking.review.enums.GameType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class ReviewFilterRequestDto {

    @Schema(description = "게임 종류",
            example = """
                    ["LIAR_GAME", "BALANCE_GAME", "OPEN_QUESTION_GAME", "COIN_TRUTH_GAME"]
                    """)
    private List<GameType> gameTypes;

    @Schema(description = "최소 평점", example = "1")
    private Integer minGrade;

    @Schema(description = "최대 평점", example = "5")
    private Integer maxGrade;

    @Schema(description = "검색 키워드", example = "대박")
    private String keyword;

}
