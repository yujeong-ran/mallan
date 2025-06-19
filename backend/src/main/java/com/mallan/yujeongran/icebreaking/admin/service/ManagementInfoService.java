package com.mallan.yujeongran.icebreaking.admin.service;

import com.mallan.yujeongran.icebreaking.admin.dto.response.ManagementInfoResponseDto;
import com.mallan.yujeongran.icebreaking.admin.enitity.ManagementInfo;
import com.mallan.yujeongran.icebreaking.admin.repository.ManagementInfoRepository;
import com.mallan.yujeongran.icebreaking.review.dto.response.ReviewStatsResponseDto;
import com.mallan.yujeongran.icebreaking.review.enums.GameType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class ManagementInfoService {

    private final ManagementInfoRepository managementInfoRepository;

    public void incrementGameCount(GameType type) {
        ManagementInfo info = managementInfoRepository.findTopByOrderByUpdatedAtDesc()
                .orElseGet(() -> managementInfoRepository.save(ManagementInfo.builder().build()));

        info.setTotalGameCount(info.getTotalGameCount() + 1);

        switch (type) {
            case LIAR_GAME -> info.setLiarTotalCount(info.getLiarTotalCount() + 1);
            case BALANCE_GAME -> info.setBalanceTotalCount(info.getBalanceTotalCount() + 1);
            case OPEN_QUESTION_GAME -> info.setQuestionTotalCount(info.getQuestionTotalCount() + 1);
            case COIN_TRUTH_GAME -> info.setCoinTotalCount(info.getCoinTotalCount() + 1);
        }

        managementInfoRepository.save(info);
    }

    public void incrementUserCount(GameType type) {
        ManagementInfo info = managementInfoRepository.findTopByOrderByUpdatedAtDesc()
                .orElseGet(() -> managementInfoRepository.save(ManagementInfo.builder().build()));

        switch (type) {
            case LIAR_GAME, BALANCE_GAME, OPEN_QUESTION_GAME, COIN_TRUTH_GAME -> info.setTotalUserCount(info.getTotalUserCount() + 1);
        }

        managementInfoRepository.save(info);
    }

    public ManagementInfoResponseDto getLatestInfo() {
        ManagementInfo info = managementInfoRepository.findTopByOrderByUpdatedAtDesc()
                .orElseGet(() -> managementInfoRepository.save(ManagementInfo.builder()
                        .totalGameCount(0)
                        .totalUserCount(0)
                        .totalReviewCount(0)
                        .averageGrade(0)
                        .liarTotalCount(0)
                        .balanceTotalCount(0)
                        .questionTotalCount(0)
                        .coinTotalCount(0)
                        .monthlyReviewCount(0)
                        .build()));

        return ManagementInfoResponseDto.builder()
                .totalGameCount(info.getTotalGameCount())
                .totalUserCount(info.getTotalUserCount())
                .totalReviewCount(info.getTotalReviewCount())
                .averageGrade(info.getAverageGrade())
                .liarTotalCount(info.getLiarTotalCount())
                .balanceTotalCount(info.getBalanceTotalCount())
                .questionTotalCount(info.getQuestionTotalCount())
                .coinTotalCount(info.getCoinTotalCount())
                .monthlyReviewCount(info.getMonthlyReviewCount())
                .build();
    }

    public void updateStatsFromReview(ReviewStatsResponseDto stats) {
        ManagementInfo info = managementInfoRepository.findTopByOrderByUpdatedAtDesc()
                .orElseThrow(() -> new IllegalStateException("관리 통계 정보가 없습니다."));

        info.setTotalReviewCount(stats.getTotalReviewCount());
        info.setAverageGrade(stats.getAverageGrade());
        info.setMonthlyReviewCount(stats.getMonthlyReviewCount());

        info.setUpdatedAt(LocalDateTime.now());
        managementInfoRepository.save(info);
    }

}
