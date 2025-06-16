package com.mallan.yujeongran.icebreaking.liar_game.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "liar_player")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LiarPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private LiarRoom room;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "is_liar", nullable = false)
    private Boolean isLiar;

    @Column(name = "profileImage", nullable = false)
    private String profileImage;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void OnCreated(){
        this.createdAt = LocalDateTime.now();
    }

}
