package com.mallan.yujeongran.icebreaking.coin_game.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coin_room")
@Builder
public class CoinRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_code", nullable = false)
    private String roomCode;

    @Column(name = "host_id", nullable = false)
    private String hostId;

    @Column(name = "host_nickname", nullable = false)
    private String hostNickname;

    @Column(name = "round_count", nullable = false)
    private int roundCount;

    @Column(name = "player_count", nullable = false)
    private int playerCount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreated() {
        this.createdAt = LocalDateTime.now();
    }

}
