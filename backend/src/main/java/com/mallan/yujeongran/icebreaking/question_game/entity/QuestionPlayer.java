package com.mallan.yujeongran.icebreaking.question_game.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "question_player")
@Builder
public class QuestionPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_code", nullable = false)
    private String roomCode;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "player_id", nullable = false)
    private String playerId;

    @Column(name = "profile_image", nullable = false)
    private String profileImage;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void OnCreated() {
        this.createdAt = LocalDateTime.now();
    }

}
