package com.mallan.yujeongran.icebreaking.question_game.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QuestionTopicResponseDto {

    private Long topicId;
    private String topic;
    private LocalDateTime createdAt;

    public QuestionTopicResponseDto(Long topicId, String topic, LocalDateTime createdAt) {
        this.topicId = topicId;
        this.topic = topic;
        this.createdAt = createdAt;
    }
}

