package com.mallan.yujeongran.icebreaking.coin_game.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.coin_game.dto.request.*;
import com.mallan.yujeongran.icebreaking.coin_game.dto.response.CoinCreateRoomResponseDto;
import com.mallan.yujeongran.icebreaking.coin_game.dto.response.CoinJoinRoomResponseDto;
import com.mallan.yujeongran.icebreaking.coin_game.dto.response.CoinWaitingRoomResponseDto;
import com.mallan.yujeongran.icebreaking.coin_game.service.CoinPlayerService;
import com.mallan.yujeongran.icebreaking.coin_game.service.CoinRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coin/room")
@RequiredArgsConstructor
@Tag(name = "Coin Room API", description = "코인 진실 게임 방 관련 API")
public class CoinRoomController {

    private final CoinRoomService coinRoomService;
    private final CoinPlayerService coinPlayerService;

    @PostMapping
    @Operation(summary = "방 생성 API", description = "닉네임과 프로필 이미지는 입력받아 방을 생성합니다.")
    public ResponseEntity<CommonResponse<CoinCreateRoomResponseDto>> createRoom(
            @RequestBody CoinCreateRoomRequestDto request
    ){
        CoinCreateRoomResponseDto response = coinPlayerService.createRoom(request);
        return ResponseEntity.ok(CommonResponse.success("방 생성 성공!", response));
    }

    @PostMapping("/{roomCode}/join")
    @Operation(summary = "방 참가 API", description = "닉네임과 프로필 이미지를 입력받아 방에 참가합니다.")
    public ResponseEntity<CommonResponse<CoinJoinRoomResponseDto>> joinRoom(
            @PathVariable String roomCode,
            @RequestBody CoinJoinRoomRequestDto request
    ){
        CoinJoinRoomResponseDto response = coinPlayerService.joinRoom(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("방 입장 성공!", response));
    }

    @GetMapping("/{roomCode}/ready")
    @Operation(summary = "대기방 정보 조회 API", description = "방장, 참가자, 라운드 수 등의 대기방 정보를 반환합니다.")
    public ResponseEntity<CommonResponse<CoinWaitingRoomResponseDto>> getWaitingRoomInfo(
            @PathVariable String roomCode
    ) {
        CoinWaitingRoomResponseDto response = coinRoomService.getWaitingRoomInfo(roomCode);
        return ResponseEntity.ok(CommonResponse.success("대기방 정보 반환 성공", response));
    }

    @PatchMapping("/{roomCode}/round")
    @Operation(summary = "라운드 수 설정", description = "방장이 라운드 수를 설정합니다.")
    public ResponseEntity<CommonResponse<String>> setRound(
            @PathVariable String roomCode,
            @RequestBody CoinSetRoundRequestDto request
    ) {
        coinRoomService.setRound(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("라운드 수 설정 성공!", null));
    }

    @PostMapping("/{roomCode}/start")
    @Operation(summary = "게임 시작", description = "방장이 게임을 시작합니다.")
    public ResponseEntity<CommonResponse<String>> startGame(
            @PathVariable String roomCode,
            @RequestBody CoinStartGameRequestDto request
    ) {
        coinRoomService.startGame(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("게임 시작 성공!", null));
    }

    @PostMapping("/{roomCode}/leave/{playerId}")
    @Operation(summary = "방 나가기", description = "플레이어가 방을 나가고, 남은 인원이 없으면 방도 삭제됩니다.")
    public ResponseEntity<CommonResponse<Void>> leaveRoom(
            @PathVariable String roomCode,
            @RequestBody CoinExitRoomRequestDto request) {
        coinRoomService.leaveRoom(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("방을 나갔습니다.", null));
    }

}
