package com.mallan.yujeongran.icebreaking.question_game.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.question_game.dto.request.*;
import com.mallan.yujeongran.icebreaking.question_game.dto.response.*;
import com.mallan.yujeongran.icebreaking.question_game.service.QuestionRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question/room")
@RequiredArgsConstructor
@Tag(name = "Question Room API", description = "오픈 퀘스천 게임 방 관련 API")
public class QuestionRoomController {

    private final QuestionRoomService questionRoomService;

    @PostMapping
    @Operation(summary = "방 생성 API", description = "닉네임과 프로필 이미지를 입력받아 방을 생성합니다.")
    public ResponseEntity<CommonResponse<QuestionCreateRoomResponseDto>> createRoom(
            @RequestBody QuestionCreateRoomRequestDto request
    ) {
        QuestionCreateRoomResponseDto response = questionRoomService.createRoom(request);
        return ResponseEntity.ok(CommonResponse.success("방 생성 성공!", response));
    }

    @PostMapping("/{roomCode}/join")
    @Operation(summary = "방 입장 API", description = "닉네임과 프로필 이미지를 입력받아 방에 입장합니다.")
    public ResponseEntity<CommonResponse<QuestionJoinRoomResponseDto>> joinRoom(
            @PathVariable String roomCode,
            @RequestBody QuestionJoinRoomRequestDto request
    ) {
        QuestionJoinRoomResponseDto response = questionRoomService.joinRoom(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("방 입장 성공!", response));
    }

    @PostMapping("/{roomCode}/exit")
    @Operation(summary = "방 퇴장 API", description = "플레이어가 방을 나가며 방 인원이 0명이면 삭제됩니다.")
    public ResponseEntity<CommonResponse<QuestionExitRoomResponseDto>> exitRoom(
            @PathVariable String roomCode,
            @RequestBody QuestionExitRoomRequestDto request
    ) {
        QuestionExitRoomResponseDto responseDto = questionRoomService.exitRoom(roomCode, request.getPlayerId());
        return ResponseEntity.ok(CommonResponse.success("방 나가기 완료", responseDto));
    }

    @PostMapping("/{roomCode}/set-question-count")
    @Operation(summary = "문제 수 설정 API", description = "방장이 문제 수를 설정합니다.")
    public ResponseEntity<CommonResponse<QuestionSetQuestionCountResponseDto>> setQuestionCount(
            @PathVariable String roomCode,
            @RequestBody QuestionSetQuestionCountRequestDto request
    ) {
        return ResponseEntity.ok(
                CommonResponse.success("문제 수 설정 성공", questionRoomService.setQuestionCount(roomCode, request))
        );
    }

    @PostMapping("/{roomCode}/set-topic")
    @Operation(summary = "주제 설정 API", description = "방장이 주제를 설정합니다.")
    public ResponseEntity<CommonResponse<QuestionSetTopicResponseDto>> setTopic(
            @PathVariable String roomCode,
            @RequestBody QuestionSetTopicRequestDto request
    ) {
        return ResponseEntity.ok(
                CommonResponse.success("주제 설정 성공", questionRoomService.setTopic(roomCode, request))
        );
    }

    @PostMapping("/{roomCode}/start")
    @Operation(summary = "게임 시작 API", description = "방장이 게임을 시작합니다.")
    public ResponseEntity<CommonResponse<Void>> startGame(
            @PathVariable String roomCode,
            @RequestBody QuestionGameStartRequestDto request
    ) {
        questionRoomService.startGame(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("게임 시작 성공", null));
    }

    @GetMapping("/{roomCode}/ready")
    @Operation(summary = "대기방 정보 조회 API", description = "방의 기본 정보와 참가자 리스트를 반환합니다.")
    public ResponseEntity<CommonResponse<QuestionWaitingRoomResponseDto>> getWaitingRoomInfo(
            @PathVariable String roomCode
    ) {
        QuestionWaitingRoomResponseDto response = questionRoomService.getWaitingRoomInfo(roomCode);
        return ResponseEntity.ok(CommonResponse.success("대기방 정보 조회 성공", response));
    }


}

