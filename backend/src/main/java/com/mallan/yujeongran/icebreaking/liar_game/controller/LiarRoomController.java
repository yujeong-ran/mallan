package com.mallan.yujeongran.icebreaking.liar_game.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.CreateLiarRoomRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.ExitLiarRoomRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.JoinLiarRoomRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.SelectTopicRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.response.LiarGameResultResponseDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.response.LiarHostVerifyResponseDto;
import com.mallan.yujeongran.icebreaking.liar_game.service.LiarRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/liar/room")
@RequiredArgsConstructor
@Tag(name = "Liar Room API", description = "라이어게임 방 관련 API")
public class LiarRoomController {

    private final LiarRoomService liarRoomService;

    @PostMapping
    @Operation(summary = "게임방 생성 API", description = "닉네임을 입력받아 방을 생성하고 URL 코드를 응답합니다.")
    public ResponseEntity<CommonResponse<Map<String, String>>> createRoom(
            @RequestBody CreateLiarRoomRequestDto request
    ) {
        Map<String, String> result = liarRoomService.createRoom(request);
        return ResponseEntity.ok(CommonResponse.success("방 생성 성공!", result));
    }

    @PostMapping("/{roomCode}/join")
    @Operation(summary = "방 입장 API", description = "닉네임을 입력받아 방에 입장합니다.")
    public ResponseEntity<CommonResponse<Void>> joinRoom(
            @PathVariable String roomCode,
            @RequestBody JoinLiarRoomRequestDto request
    ) {
        liarRoomService.joinRoom(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("입장 성공!"));
    }

    @PostMapping("/{roomCode}/Exit")
    @Operation(summary = "방 퇴장 API", description = "플레이어가 방에서 나갑니다.")
    public ResponseEntity<CommonResponse<Void>> leaveRoom(
            @PathVariable String roomCode,
            @RequestBody ExitLiarRoomRequestDto request
    ) {
        liarRoomService.ExitRoom(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("퇴장 성공!"));
    }

    @PatchMapping("/{roomCode}/topic")
    @Operation(summary = "주제 선택 API", description = "방장이 주제를 선택하고 단어를 설정합니다.")
    public ResponseEntity<CommonResponse<Void>> selectTopic(
            @PathVariable String roomCode,
            @RequestBody SelectTopicRequestDto request
    ) {
        liarRoomService.selectTopic(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("주제 선택 성공!"));
    }

    @PatchMapping("/{roomCode}/round")
    @Operation(summary = "설명 라운드 설정 API", description = "방장이 설명할 라운드(사이클) 수를 설정합니다.")
    public ResponseEntity<CommonResponse<Void>> updateDescriptionCount(
            @PathVariable String roomCode,
            @RequestParam("round") int round,
            @RequestBody LiarHostVerifyResponseDto request
    ) {
        liarRoomService.updateDescriptionCount(roomCode, round, request.getHostId());
        return ResponseEntity.ok(CommonResponse.success("설명 라운드 수 변경 완료!"));
    }

    @GetMapping("/{roomCode}/count")
    @Operation(summary = "방 참가자 수 조회 API", description = "현재 방에 몇 명이 있는지 반환합니다.")
    public ResponseEntity<CommonResponse<Integer>> getPlayerCount(
            @PathVariable String roomCode
    ) {
        int count = liarRoomService.getPlayerCount(roomCode);
        return ResponseEntity.ok(CommonResponse.success("참가자 수 조회 성공", count));
    }

    @DeleteMapping("/{roomCode}")
    @Operation(summary = "방 삭제 API", description = "방을 수동으로 삭제합니다.")
    public ResponseEntity<CommonResponse<Void>> deleteRoom(
            @PathVariable String roomCode
    ) {
        liarRoomService.deleteRoom(roomCode);
        return ResponseEntity.ok(CommonResponse.success("방 삭제 완료!"));
    }

    @GetMapping("/{roomCode}/word")
    @Operation(summary = "플레이어 단어 조회 API", description = "플레이어가 라이어인지 여부에 따라 단어를 반환합니다.")
    public ResponseEntity<CommonResponse<Map<String, String>>> getWordForPlayer(
            @PathVariable String roomCode,
            @RequestParam("playerId") String playerId
    ) {
        String word = liarRoomService.getWordForPlayer(roomCode, playerId);
        Map<String, String> data = new HashMap<>();
        data.put("word", word);
        return ResponseEntity.ok(CommonResponse.success("단어 조회 성공!", data));
    }

    @GetMapping("/{roomCode}/voted-liar")
    @Operation(summary = "가장 많이 투표된 플레이어 조회", description = "집계된 결과로 가장 많이 표를 받은 플레이어 ID를 반환합니다.")
    public ResponseEntity<CommonResponse<Map<String, String>>> getVotedLiar(
            @PathVariable String roomCode
    ) {
        Map<String, String> result = liarRoomService.getVotedLiar(roomCode);
        return ResponseEntity.ok(CommonResponse.success("최다 투표자 조회 성공!", result));
    }

    @GetMapping("/{roomCode}/result")
    @Operation(summary = "게임 결과 제공 API", description = "게임의 결과를 제공받습니다.")
    public ResponseEntity<CommonResponse<LiarGameResultResponseDto>> getGameResult(
            @PathVariable String roomCode
    ) {
        LiarGameResultResponseDto result = liarRoomService.getGameResult(roomCode);
        return ResponseEntity.ok(CommonResponse.success("게임 결과 반환 성공!", result));
    }

    @PatchMapping("/{roomCode}/start")
    @Operation(summary = "게임 시작 API", description = "게임을 시작하고 라이어를 배정합니다.")
    public ResponseEntity<CommonResponse<Void>> startGame(
            @PathVariable String roomCode,
            @RequestBody LiarHostVerifyResponseDto request
    ) {
        liarRoomService.startGame(roomCode, request.getHostId());
        return ResponseEntity.ok(CommonResponse.success("게임 시작 완료!"));
    }

    @PatchMapping("/{roomCode}/restart")
    @Operation(summary = "게임 재시작 API", description = "이전 게임 결과를 초기화하고 새 라이어를 배정합니다.")
    public ResponseEntity<CommonResponse<Void>> restartGame(
            @PathVariable String roomCode,
            @RequestBody LiarHostVerifyResponseDto request
    ) {
        liarRoomService.restartGame(roomCode, request.getHostId());
        return ResponseEntity.ok(CommonResponse.success("게임 재시작 완료!"));
    }

}
