package com.mallan.yujeongran.icebreaking.coin_game.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coin_question")
@Builder
public class CoinQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question", nullable = false, length = 200)
    private String question;

    @Column(name = "expected_count", nullable = false)
    private int expectedCount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void OnCreated() {
        this.createdAt = LocalDateTime.now();
    }

}
