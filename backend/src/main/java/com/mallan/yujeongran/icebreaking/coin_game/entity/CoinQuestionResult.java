package com.mallan.yujeongran.icebreaking.coin_game.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coin_question_result")
@Builder
public class CoinQuestionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "yes_count", nullable = false)
    private int yesCount;

    @Column(name = "no_count", nullable = false)
    private int noCount;

    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;

}
