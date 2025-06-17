package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarSelectTopicRequestDto {

    @Schema(description = "호스트 아이디", example = "a1b2c3d4")
    private String hostId;

    @Schema(description = "주제 아이디", example = "2")
    private Long topicId;

    public LiarSelectTopicRequestDto(String hostId, Long topicId){
        this.hostId = hostId;
        this.topicId = topicId;
    }

}

