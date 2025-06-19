package com.mallan.yujeongran.icebreaking.admin.enitity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "management_info")
public class ManagementInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_game_count", nullable = false)
    private int totalGameCount;

    @Column(name = "total_user_count", nullable = false)
    private int totalUserCount;

    @Column(name = "total_review_count", nullable = false)
    private int totalReviewCount;

    @Column(name = "average_grade", nullable = false)
    private float averageGrade;

    @Column(name = "liar_total_count", nullable = false)
    private int liarTotalCount;

    @Column(name = "balance_total_count", nullable = false)
    private int balanceTotalCount;

    @Column(name = "question_total_count", nullable = false)
    private int questionTotalCount;

    @Column(name = "coin_total_count", nullable = false)
    private int coinTotalCount;

    @Column(name = "monthly_review_count", nullable = false)
    private int monthlyReviewCount;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void OnUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
