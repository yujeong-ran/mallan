package com.mallan.yujeongran.icebreaking.review.enitity;

import com.mallan.yujeongran.icebreaking.review.enums.GameType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "review")
@Data
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_type", nullable = false)
    private GameType gameType;

    @Column(name = "nickname", nullable = false)
    private  String nickname;

    @Column(name = "grade", nullable = false)
    private  int grade;

    @Column(name = "content",nullable = false)
    private  String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void OnCreate() {
        this.createdAt = LocalDateTime.now();
    }

}