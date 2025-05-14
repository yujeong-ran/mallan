package com.mallan.yujeongran.icebreaking.liar_game.controller;

import com.mallan.yujeongran.icebreaking.liar_game.dto.request.CreateLiarRoomRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.ExitLiarRoomRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.JoinLiarRoomRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.SelectTopicRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.service.LiarRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/liar/room")
@RequiredArgsConstructor
@Tag(name = "Liar Room API", description = "라이어게임 방 관련 API")
public class LiarRoomController {

    private final LiarRoomService liarRoomService;

    @PostMapping
    @Operation(summary = "게임방 생성 API", description = "닉네임을 입력받아 방을 생성하고 URL 코드를 응답합니다.")
    public ResponseEntity<String> createRoom(
            @RequestBody CreateLiarRoomRequestDto request
    ) {
        String roomCode = liarRoomService.createRoom(request);
        return ResponseEntity.ok(roomCode);
    }

    @PostMapping("/{roomCode}/join")
    @Operation(summary = "방 입장 API", description = "닉네임을 입력받아 방에 입장합니다.")
    public ResponseEntity<String> joinRoom(
            @PathVariable String roomCode,
            @RequestBody JoinLiarRoomRequestDto request
    ) {
        liarRoomService.joinRoom(roomCode, request);
        return ResponseEntity.ok("입장 성공!");
    }

    @PostMapping("/{roomCode}/Exit")
    @Operation(summary = "방 퇴장 API", description = "플레이어가 방에서 나갑니다.")
    public ResponseEntity<String> leaveRoom(
            @PathVariable String roomCode,
            @RequestBody ExitLiarRoomRequestDto request
    ) {
        liarRoomService.ExitRoom(roomCode, request);
        return ResponseEntity.ok("퇴장 성공!");
    }

    @PatchMapping("/{roomCode}/topic")
    @Operation(summary = "방장이 주제 선택 API", description = "방장이 주제를 선택하고 단어를 설정합니다.")
    public ResponseEntity<String> selectTopic(
            @PathVariable String roomCode,
            @RequestBody SelectTopicRequestDto request
    ) {
        liarRoomService.selectTopic(roomCode, request);
        return ResponseEntity.ok("주제 선택 성공!");
    }

    @GetMapping("/{roomCode}/count")
    @Operation(summary = "방 참가자 수 조회 API", description = "현재 방에 몇 명이 있는지 반환합니다.")
    public ResponseEntity<Integer> getPlayerCount(
            @PathVariable String roomCode
    ) {
        int count = liarRoomService.getPlayerCount(roomCode);
        return ResponseEntity.ok(count);
    }

    @DeleteMapping("/{roomCode}")
    @Operation(summary = "방 삭제 API", description = "방을 수동으로 삭제합니다.")
    public ResponseEntity<String> deleteRoom(@PathVariable String roomCode) {
        liarRoomService.deleteRoom(roomCode);
        return ResponseEntity.ok("방 삭제 완료");
    }

}
