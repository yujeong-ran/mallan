package com.mallan.yujeongran.icebreaking.question_game.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "question_room")
@Builder
public class QuestionRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_code", nullable = false)
    private String roomCode;

    @Column(name = "host_id", nullable = false)
    private String hostId;

    @Column(name = "host_nickname", nullable = false)
    private String hostNickname;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "player_count", nullable = false)
    private int playerCount;

    @Column(name = "question_count", nullable = false)
    private int questionCount;

    @Column(name = "topic_id", nullable = false)
    private int topicId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void OnCreated() {
        this.createdAt = LocalDateTime.now();
    }

}
