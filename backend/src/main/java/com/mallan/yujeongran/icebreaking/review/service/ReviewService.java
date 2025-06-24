package com.mallan.yujeongran.icebreaking.review.service;

import com.mallan.yujeongran.icebreaking.review.dto.request.ReviewDeleteRequestDto;
import com.mallan.yujeongran.icebreaking.review.dto.request.ReviewFilterRequestDto;
import com.mallan.yujeongran.icebreaking.review.dto.request.ReviewRequestDto;
import com.mallan.yujeongran.icebreaking.review.dto.response.ReviewResponseDto;
import com.mallan.yujeongran.icebreaking.review.dto.response.ReviewStatsResponseDto;
import com.mallan.yujeongran.icebreaking.review.enitity.Review;
import com.mallan.yujeongran.icebreaking.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewResponseDto createReview(ReviewRequestDto request) {
        Review review = Review.builder()
                .gameType(request.getGameType())
                .nickname(request.getNickname())
                .grade(request.getGrade())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        Review saved = reviewRepository.save(review);

        return ReviewResponseDto.builder()
                .gameType(saved.getGameType())
                .nickname(saved.getNickname())
                .grade(saved.getGrade())
                .content(saved.getContent())
                .build();
    }

    public List<ReviewResponseDto> getRecentTwoReviews() {
        return reviewRepository.findTop2ByOrderByCreatedAtDesc()
                .stream()
                .map(ReviewResponseDto::from)
                .toList();
    }

    public List<ReviewResponseDto> getRecentThreeReviews() {
        return reviewRepository.findTop3ByOrderByCreatedAtDesc()
                .stream()
                .map(ReviewResponseDto::from)
                .toList();
    }

    public ReviewStatsResponseDto getReviewStats() {
        LocalDateTime now = LocalDateTime.now();

        Float rawAverage = Optional.ofNullable(reviewRepository.findAverageGrade()).orElse(0.0f);
        float roundedAverage = Math.round(rawAverage * 10) / 10.0f;

        return ReviewStatsResponseDto.builder()
                .totalReviewCount((int) reviewRepository.count())
                .averageGrade(roundedAverage)
                .monthlyReviewCount(reviewRepository.countReviewsByMonth(now.getYear(), now.getMonthValue()))
                .build();
    }

    public List<ReviewResponseDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(ReviewResponseDto::from)
                .toList();
    }

    public void deleteReview(ReviewDeleteRequestDto request) {
        if (!reviewRepository.existsById(request.getReviewId())) {
            throw new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다.");
        }
        reviewRepository.deleteById(request.getReviewId());
    }

    public List<ReviewResponseDto> filterReviews(ReviewFilterRequestDto request) {
        List<Review> reviews = reviewRepository.filterReviews(
                request.getGameTypes() == null || request.getGameTypes().isEmpty() ? null : request.getGameTypes(),
                request.getMinGrade(),
                request.getMaxGrade(),
                request.getKeyword() == null || request.getKeyword().isEmpty() ? null : request.getKeyword()
        );

        return reviews.stream()
                .map(ReviewResponseDto ::from)
                .toList();
    }

}
