package com.mallan.yujeongran.icebreaking.balance_game.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.*;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceJoinRoomResponseDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceRoomResponseDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceWaitingRoomResponseDto;
import com.mallan.yujeongran.icebreaking.balance_game.service.BalanceRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/balance/room")
@Tag(name = "Balance Room API", description = "밸런스 게임 방 관련 API")
public class BalanceRoomController {

    private final BalanceRoomService balanceRoomService;

    @PostMapping
    @Operation(summary = "방 생성 API", description = "닉네임과 프로필을 선택 후 방과 사용자를 생성합니다.")
    public ResponseEntity<CommonResponse<Map<String, String>>> createRoom(
            @RequestBody BalanceCreatePlayerRequestDto request
    ) {
        BalanceRoomResponseDto result = balanceRoomService.createRoomWithHost(request);

        Map<String, String> response = new HashMap<>();
        response.put("playerId", result.getPlayerId());
        response.put("roomCode", result.getRoomCode());
        response.put("url", result.getUrl());

        return ResponseEntity.ok(CommonResponse.success("방 생성 완료!", response));
    }

    @PostMapping("/{roomCode}/join")
    @Operation(summary = "방 입장 API", description = "초대 URL을 통해 닉네임과 프로필을 선택 후 방에 참가합니다.")
    public ResponseEntity<CommonResponse<BalanceJoinRoomResponseDto>> joinRoom(
            @PathVariable String roomCode,
            @RequestBody BalanceJoinRoomRequestDto request
    ) {
        BalanceJoinRoomResponseDto response = balanceRoomService.joinRoomAsGuest(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("방 참가 완료!", response));
    }


    @GetMapping("/{roomCode}/ready")
    @Operation(summary = "대기방 조회 API", description = "방의 기본 정보와 참가자 목록을 반환합니다.")
    public ResponseEntity<CommonResponse<BalanceWaitingRoomResponseDto>> getWaitingRoomInfo(
            @PathVariable String roomCode
    ) {
        BalanceWaitingRoomResponseDto response = balanceRoomService.getWaitingRoomInfo(roomCode);
        return ResponseEntity.ok(CommonResponse.success("대기방 조회 성공!", response));
    }

    @PatchMapping("/{roomCode}/topic")
    @Operation(summary = "주제 선택 API", description = "방장이 주제를 선택합니다.")
    public ResponseEntity<CommonResponse<Void>> updateRoomTopic(
            @PathVariable String roomCode,
            @RequestBody BalanceUpdateTopicRequestDto request
    ) {
        balanceRoomService.updateRoomTopic(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("주제 설정 완료!"));
    }

    @PatchMapping("/{roomCode}/question-count")
    @Operation(summary = "문항 수 선택 API", description = "방장이 문항 수를 선택합니다.")
    public ResponseEntity<CommonResponse<Void>> updateRoomQuestionCount(
            @PathVariable String roomCode,
            @RequestBody BalanceUpdateQuestionCountRequestDto request
    ) {
        balanceRoomService.updateRoomQuestionCount(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("문항 수 설정 완료!"));
    }

    @PatchMapping("/{roomCode}/leave")
    @Operation(summary = "대기방 나가기 API", description = "플레이어가 대기방에서 나갑니다. 마지막 사람이라면 방도 삭제됩니다.")
    public ResponseEntity<CommonResponse<Void>> leaveWaitingRoom(
            @PathVariable String roomCode,
            @RequestBody BalanceExitRoomRequestDto request
    ) {
        balanceRoomService.exitRoom(roomCode, request.getPlayerId());
        return ResponseEntity.ok(CommonResponse.success("대기방 퇴장 성공!"));
    }

}
