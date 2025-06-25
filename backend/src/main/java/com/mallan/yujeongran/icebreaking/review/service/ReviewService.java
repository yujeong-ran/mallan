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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        if(request.getGrade() < 1 || 5 < request.getGrade()) {
            throw new IllegalArgumentException("평점은 1에서 5사이의 정수여야 합니다.");
        }

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

    public Page<ReviewResponseDto> filterReviews(ReviewFilterRequestDto request, int page) {
        int size = 6;
        Pageable pageable = PageRequest.of(page, size);

        List<String> gameTypeStrings = request.getGameTypes() == null ? null :
                request.getGameTypes().stream()
                        .map(Enum::name)
                        .toList();

        Page<Review> reviews = reviewRepository.filterReviews(
                gameTypeStrings,
                request.getMinGrade(),
                request.getMaxGrade(),
                request.getKeyword() == null || request.getKeyword().isEmpty() ? null : request.getKeyword(),
                pageable
        );

        return reviews.map(ReviewResponseDto ::from);
    }

}
