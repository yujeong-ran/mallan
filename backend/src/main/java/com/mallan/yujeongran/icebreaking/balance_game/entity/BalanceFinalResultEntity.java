package com.mallan.yujeongran.icebreaking.balance_game.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "balance_final_result")
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class BalanceFinalResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_id", nullable = false)
    private String playerId;

    @Column(name = "compared_player_id", nullable = false)
    private String comparedPlayerId;

    @Column(name = "match_rate", nullable = false)
    private int matchRate;

    @ManyToOne(fetch = FetchType.LAZY)
    private BalanceRoom room;
}

