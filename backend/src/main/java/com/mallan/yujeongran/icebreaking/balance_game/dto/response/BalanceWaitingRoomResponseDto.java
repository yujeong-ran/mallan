package com.mallan.yujeongran.icebreaking.balance_game.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BalanceWaitingRoomResponseDto {

    private String roomCode;
    private String hostNickname;
    private Long topicId;
    private int questionCount;
    private List<BalancePlayerInfoResponseDto> players;

}

