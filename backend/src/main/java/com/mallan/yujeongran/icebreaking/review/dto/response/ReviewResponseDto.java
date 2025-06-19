package com.mallan.yujeongran.icebreaking.review.dto.response;

import com.mallan.yujeongran.icebreaking.review.enitity.Review;
import com.mallan.yujeongran.icebreaking.review.enums.GameType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewResponseDto {

    private GameType gameType;
    private String nickname;
    private int grade;
    private String content;
    private LocalDateTime createdAt;

    public static ReviewResponseDto from(Review review) {
        return ReviewResponseDto.builder()
                .gameType(review.getGameType())
                .nickname(review.getNickname())
                .grade(review.getGrade())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .build();
    }

}
