package com.mallan.yujeongran.icebreaking.liar_game.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.*;
import com.mallan.yujeongran.icebreaking.liar_game.dto.response.LiarGameResultResponseDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.response.LiarHostVerifyResponseDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.response.LiarWaitingRoomResponseDto;
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
    @Operation(summary = "방 생성 API", description = "닉네임을 입력받아 플레이어와 방을 동시에 생성합니다.")
    public ResponseEntity<CommonResponse<Map<String, String>>> createRoomWithHost(
            @RequestBody LiarCreatePlayerWithRoomRequestDto request
    ) {
        Map<String, String> result = liarRoomService.createRoomWithHost(request);
        return ResponseEntity.ok(CommonResponse.success("방 및 플레이어 생성 완료!", result));
    }

    @PostMapping("/{roomCode}/join")
    @Operation(summary = "방 입장 API", description = "닉네임과 프로필을 입력받아 플레이어 생성 후 방에 입장합니다.")
    public ResponseEntity<CommonResponse<Map<String, String>>> joinRoomWithPlayer(
            @PathVariable String roomCode,
            @RequestBody LiarJoinRoomRequestDto request
    ) {
        Map<String, String> result = liarRoomService.joinRoomWithPlayer(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("플레이어 생성 및 방 입장 성공!", result));
    }


    @PostMapping("/{roomCode}/exit")
    @Operation(summary = "방 퇴장 API", description = "플레이어가 방에서 나갑니다.")
    public ResponseEntity<CommonResponse<Void>> leaveRoom(
            @PathVariable String roomCode,
            @RequestBody LiarExitRoomRequestDto request
    ) {
        liarRoomService.ExitRoom(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("퇴장 성공!"));
    }

    @GetMapping("/{roomCode}/ready")
    @Operation(summary = "대기방 정보 조회 API", description = "방의 기본 정보와 참가자 목록을 반환합니다.")
    public ResponseEntity<CommonResponse<LiarWaitingRoomResponseDto>> getWaitingRoomInfo(
            @PathVariable String roomCode
    ) {
        LiarWaitingRoomResponseDto response = liarRoomService.getWaitingRoomInfo(roomCode);
        return ResponseEntity.ok(CommonResponse.success("대기방 조회 성공!", response));
    }

    @PatchMapping("/{roomCode}/topic")
    @Operation(summary = "주제 선택 API", description = "방장이 주제를 선택하고 단어를 설정합니다.")
    public ResponseEntity<CommonResponse<Void>> selectTopic(
            @PathVariable String roomCode,
            @RequestBody LiarSelectTopicRequestDto request
    ) {
        liarRoomService.selectTopic(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("주제 선택 성공!"));
    }

    @PatchMapping("/{roomCode}/round")
    @Operation(summary = "설명 라운드 설정 API", description = "방장이 설명할 라운드(사이클) 수를 설정합니다.")
    public ResponseEntity<CommonResponse<Void>> updateDescriptionCount(
            @PathVariable String roomCode,
            @RequestBody LiarUpdateRoundRequestDto request
    ) {
        liarRoomService.updateDescriptionCount(roomCode, request.getRound(), request.getHostId());
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

    @PatchMapping("/{roomCode}/end")
    @Operation(summary = "게임 종료 API", description = "방장이 게임 종료를 하면 방이 삭제됩니다.")
    public ResponseEntity<CommonResponse<Void>> deleteRoom(
            @PathVariable String roomCode,
            @RequestBody LiarEndGameRequestDto request
    ) {
        liarRoomService.deleteRoom(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("방 삭제 완료!"));
    }

    @PostMapping("/{roomCode}/word")
    @Operation(summary = "플레이어 단어 조회 API", description = "플레이어가 라이어인지 여부에 따라 단어를 반환합니다.")
    public ResponseEntity<CommonResponse<Map<String, String>>> getWordForPlayer(
            @PathVariable String roomCode,
            @RequestBody LiarSearchPlayerWordRequestDto request
    ) {
        String word = liarRoomService.getWordForPlayer(roomCode, request);
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
