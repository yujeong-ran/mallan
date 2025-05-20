package com.mallan.yujeongran.icebreaking.balance_game.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "balance_question")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
public class BalanceQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private BalanceTopic topic;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "choice_a", nullable = false)
    private String choiceA;

    @Column(name = "choice_b", nullable = false)
    private String choiceB;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
