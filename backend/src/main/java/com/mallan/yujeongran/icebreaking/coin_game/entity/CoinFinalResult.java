package com.mallan.yujeongran.icebreaking.coin_game.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coin_final_result")
@Builder
public class CoinFinalResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_code", nullable = false)
    private String roomCode;

    @Column(name = "player_id", nullable = false)
    private String playerId;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "score", nullable = false)
    private int score;

    @Column(name = "profileImage", nullable = false)
    private String profileImage;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void OnCreated() {
        this.createdAt = LocalDateTime.now();
    }

}