package com.mallan.yujeongran.icebreaking.review.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.review.dto.request.ReviewRequestDto;
import com.mallan.yujeongran.icebreaking.review.dto.response.ReviewResponseDto;
import com.mallan.yujeongran.icebreaking.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Tag(name = "Review API", description = "리뷰 작성 관련 API")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @Operation(
            summary = "리뷰 작성 API",
            description =
        """
        게임, 닉네임, 평점, 내용을 입력해서 리뷰를 작성합니다.

        🔹 게임 종류는 다음 중 하나를 입력하세요:
        - LIAR_GAME
        - BALANCE_GAME
        - OPEN_QUESTION_GAME
        - COIN_TRUTH_GAME
        """
    )
    public ResponseEntity<CommonResponse<ReviewResponseDto>> createReview(
            @RequestBody ReviewRequestDto request
    ) {
        ReviewResponseDto reviewResponseDto = reviewService.createReview(request);
        return ResponseEntity.ok(CommonResponse.success("리뷰 작성 성공!", reviewResponseDto));
    }

}
