package com.mallan.yujeongran.icebreaking.liar_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LiarWebSocketSelectTopicRequestDto {

    @Schema(description = "방 코드", example = "1234asdf")
    private String roomCode;

    @Schema(description = "호스트 아이디", example = "a1s2d3f4")
    private String hostId;

    @Schema(description = "주제 아이디", example = "1")
    private Long topicId;

}
