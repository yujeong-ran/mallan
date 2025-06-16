package com.mallan.yujeongran.icebreaking.review.dto.response;

import com.mallan.yujeongran.icebreaking.review.enums.GameType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponseDto {

    private GameType gameType;
    private String nickname;
    private int grade;
    private String content;

}
