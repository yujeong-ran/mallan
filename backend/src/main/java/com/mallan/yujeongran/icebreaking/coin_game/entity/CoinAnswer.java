package com.mallan.yujeongran.icebreaking.coin_game.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coin_answer")
@Builder
public class CoinAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "question_id", nullable = false)
    private long questionId;

    @Column(name= "player_id", nullable = false)
    private String playerId;

    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void OnCreated(){
        this.createdAt = LocalDateTime.now();
    }

}
