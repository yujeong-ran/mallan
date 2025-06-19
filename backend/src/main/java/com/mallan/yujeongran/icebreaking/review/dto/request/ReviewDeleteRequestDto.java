package com.mallan.yujeongran.icebreaking.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ReviewDeleteRequestDto {

    @Schema(description = "리뷰 아이디", example = "1")
    private Long reviewId;

}
