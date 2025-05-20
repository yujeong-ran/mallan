package com.mallan.yujeongran.icebreaking.balance_game.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.CreateBalancePlayerRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.JoinBalanceRoomRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.UpdateBalanceQuestionCountRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.UpdateBalanceTopicRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceRoomResponseDto;
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
            @RequestBody CreateBalancePlayerRequestDto request
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
    public ResponseEntity<CommonResponse<Map<String, String>>> joinRoom(
            @PathVariable String roomCode,
            @RequestBody JoinBalanceRoomRequestDto request
    ) {
        String playerId = balanceRoomService.joinRoomAsGuest(roomCode, request.getNickname(), request.getProfileImage());

        Map<String, String> response = new HashMap<>();
        response.put("playerId", playerId);

        return ResponseEntity.ok(CommonResponse.success("방 참가 완료!", response));
    }

    @PatchMapping("/{roomCode}/topic")
    @Operation(summary = "주제 선택 API", description = "방장이 주제를 선택합니다.")
    public ResponseEntity<CommonResponse<Void>> updateRoomTopic(
            @PathVariable String roomCode,
            @RequestBody UpdateBalanceTopicRequestDto request
    ) {
        balanceRoomService.updateRoomTopic(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("주제 설정 완료!"));
    }

    @PatchMapping("/{roomCode}/question-count")
    @Operation(summary = "문항 수 선택 API", description = "방장이 문항 수를 선택합니다.")
    public ResponseEntity<CommonResponse<Void>> updateRoomQuestionCount(
            @PathVariable String roomCode,
            @RequestBody UpdateBalanceQuestionCountRequestDto request
    ) {
        balanceRoomService.updateRoomQuestionCount(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("문항 수 설정 완료!"));
    }

}


