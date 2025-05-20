package com.mallan.yujeongran.icebreaking.balance_game.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "balance_player")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
public class BalancePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private BalanceRoom roomId;

    @Column(nullable = false)
    private String playerId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profileImage;

    @Column(nullable = false)
    private int currentQuestionIndex;

    @Column(nullable = false)
    private boolean isDone;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
