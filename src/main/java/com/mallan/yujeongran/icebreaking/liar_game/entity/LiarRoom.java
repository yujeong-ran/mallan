package com.mallan.yujeongran.icebreaking.liar_game.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "liar_room")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LiarRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_code", nullable = false, unique = true)
    private String roomCode;

    @Column(name = "host_id", nullable = false)
    private String hostId;

    @Column(name = "host_nickname", nullable = false)
    private String hostNickname;

    @Column(name = "topic")
    private String topic;

    @Column(name = "word_for_liar")
    private String wordForLiar;

    @Column(name = "word_for_player")
    private String wordForPlayer;

    @Column(name = "player_count")
    private int playerCount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LiarPlayer> players;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
