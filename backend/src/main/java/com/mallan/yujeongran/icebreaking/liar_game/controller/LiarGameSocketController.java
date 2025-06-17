package com.mallan.yujeongran.icebreaking.liar_game.controller;

import com.mallan.yujeongran.icebreaking.liar_game.dto.request.*;
import com.mallan.yujeongran.icebreaking.liar_game.dto.response.LiarWaitingRoomResponseDto;
import com.mallan.yujeongran.icebreaking.liar_game.service.LiarRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LiarGameSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final LiarRoomService liarRoomService;


    /**
     * 주제 변경 시 호출되는 WebSocket 핸들러
     * - 호스트가 주제를 변경하면 해당 정보를 모든 사용자에게 브로드캐스트함
     * - 클라이언트가 /app/liar/change-topic 으로 전송해야 함
     */
    @MessageMapping("/liar/change-topic")
    public void selectTopic(@Payload LiarWebSocketSelectTopicRequestDto request) {
        // Convert to REST-only DTO (Requires addition of constructor)
        LiarSelectTopicRequestDto restRequest = new LiarSelectTopicRequestDto(request.getHostId(), request.getTopicId());

        liarRoomService.selectTopic(request.getRoomCode(), restRequest);

        // Live Topic Change Broadcast
        messagingTemplate.convertAndSend(
                "/topic/liar-room/" + request.getRoomCode(),
                request
        );
    }


    /**
     * 라운드 수 변경 시 호출되는 WebSocket 핸들러
     * - 호스트가 설명 라운드를 설정하면 방 정보 전체를 최신화하여 브로드캐스트
     */
    @MessageMapping("/liar/update-round")
    public void updateRound(
            @Payload LiarWebSocketUpdateRoundRequestDto request) {
        liarRoomService.updateDescriptionCount(request.getRoomCode(), request.getRound(), request.getHostId());

        // Get the latest waiting room status
        LiarWaitingRoomResponseDto waitingRoom = liarRoomService.getWaitingRoomInfo(request.getRoomCode());

        // Send all participants the latest waiting room information
        messagingTemplate.convertAndSend("/topic/liar-room/" + request.getRoomCode(), waitingRoom);
    }

    /**
     * 플레이어가 방에서 퇴장할 때 호출되는 WebSocket 핸들러
     * - 퇴장 처리 후 전체 사용자에게 최신 대기방 정보 전송
     */
    @MessageMapping("/liar/player-exit")
    public void playerExit(
            @Payload LiarWebSocketExitRoomRequestDto request
    ) {
        // Exit Process
        liarRoomService.ExitRoom(request.getRoomCode(), request.toDto());

        // Check the latest waiting room information
        LiarWaitingRoomResponseDto waitingRoom = liarRoomService.getWaitingRoomInfo(request.getRoomCode());

        // Broadcast to all clients
        messagingTemplate.convertAndSend("/topic/liar-room/" + request.getRoomCode(), waitingRoom);
    }

    /**
     * 게스트가 방에 입장할 때 호출되는 WebSocket 핸들러
     * - 입장 처리 후 대기방 정보를 전체 사용자에게 브로드캐스트
     */
    @MessageMapping("/liar/player-join")
    public void joinRoom(
            @Payload LiarWebSocketJoinRoomRequestDto request
    ) {
        // Convert To Dto
        LiarJoinRoomRequestDto joinRequest = new LiarJoinRoomRequestDto(
                request.getNickname(),
                request.getProfileImage()
        );

        // Call Service
        liarRoomService.joinRoomWithPlayer(request.getRoomCode(), joinRequest);

        // Return latest room status information
        LiarWaitingRoomResponseDto waitingRoom = liarRoomService.getWaitingRoomInfo(request.getRoomCode());

        // Broadcast to all clients
        messagingTemplate.convertAndSend("/topic/liar-room/" + request.getRoomCode(), waitingRoom);
    }

}
