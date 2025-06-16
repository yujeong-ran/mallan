package com.mallan.yujeongran.icebreaking.review.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewStatsResponseDto {

    private int totalReviewCount;
    private float averageGrade;
    private int monthlyReviewCount;

}
