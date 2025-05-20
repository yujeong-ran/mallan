package com.mallan.yujeongran.icebreaking.balance_game.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "balance_topic")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
public class BalanceTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String topicName;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
