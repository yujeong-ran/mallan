package com.mallan.yujeongran.icebreaking.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ManagementInfoResponseDto {

    private int totalGameCount;
    private int totalUserCount;
    private int totalReviewCount;
    private float averageGrade;
    private int liarTotalCount;
    private int balanceTotalCount;
    private int questionTotalCount;
    private int coinTotalCount;
    private int monthlyReviewCount;

}
