package com.mallan.yujeongran.icebreaking.review.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.admin.service.ManagementInfoService;
import com.mallan.yujeongran.icebreaking.review.dto.request.ReviewDeleteRequestDto;
import com.mallan.yujeongran.icebreaking.review.dto.request.ReviewFilterRequestDto;
import com.mallan.yujeongran.icebreaking.review.dto.request.ReviewRequestDto;
import com.mallan.yujeongran.icebreaking.review.dto.response.ReviewResponseDto;
import com.mallan.yujeongran.icebreaking.review.dto.response.ReviewStatsResponseDto;
import com.mallan.yujeongran.icebreaking.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Tag(name = "Review API", description = "리뷰 작성 관련 API")
public class ReviewController {

    private final ReviewService reviewService;
    private final ManagementInfoService managementInfoService;

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

        ReviewStatsResponseDto stats = reviewService.getReviewStats();
        managementInfoService.updateStatsFromReview(stats);

        return ResponseEntity.ok(CommonResponse.success("리뷰 작성 성공!", reviewResponseDto));
    }

    @GetMapping("/recent/two")
    @Operation(summary = "최근 2건의 리뷰 조회 API", description = "최근 리뷰 2건을 조회합니다.")
    public ResponseEntity<CommonResponse<List<ReviewResponseDto>>> getRecentTwoReviews() {
        return ResponseEntity.ok(CommonResponse.success("최근 리뷰 2건 조회", reviewService.getRecentTwoReviews()));
    }

    @GetMapping("/recent/three")
    @Operation(summary = "최근 3건의 리뷰 조회 API", description = "최근 리뷰 3건을 조회합니다.")
    public ResponseEntity<CommonResponse<List<ReviewResponseDto>>> getRecentThreeReviews() {
        return ResponseEntity.ok(CommonResponse.success("최근 리뷰 2건 조회", reviewService.getRecentThreeReviews()));
    }

    @GetMapping("/stats")
    @Operation(summary = "리뷰 통계 조회 API", description = "총 후기 수, 평균 평점, 이번달 리뷰 수를 한번에 조회합니다.")
    public ResponseEntity<CommonResponse<ReviewStatsResponseDto>> getReviewStats() {
        return ResponseEntity.ok(CommonResponse.success("리뷰 통계 조회 성공", reviewService.getReviewStats()));
    }

    @GetMapping("/all")
    @Operation(summary = "전체 리뷰 조회 API (관리자 전용)", description = "모든 리뷰를 조회합니다.")
    public ResponseEntity<CommonResponse<List<ReviewResponseDto>>> getAllReviews() {
        return ResponseEntity.ok(CommonResponse.success("전체 리뷰 조회 성공", reviewService.getAllReviews()));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "리뷰 삭제 API (관리자 전용)", description = "특정 리뷰를 삭제합니다.")
    public ResponseEntity<CommonResponse<Void>> deleteReview(
            @RequestBody ReviewDeleteRequestDto request
            ) {
        reviewService.deleteReview(request);
        return ResponseEntity.ok(CommonResponse.success("리뷰 삭제 성공", null));
    }

    @PostMapping("/filter")
    @Operation(summary = "리뷰 필터링 API", description = "게임 종류, 평점, 키워드로 리뷰를 필터링 합니다.")
    public ResponseEntity<CommonResponse<List<ReviewResponseDto>>> filterReview (
            @RequestBody ReviewFilterRequestDto request
    ){
        List<ReviewResponseDto> response = reviewService.filterReviews(request);
        return ResponseEntity.ok(CommonResponse.success("필터링 성공!", response));
    }

}
