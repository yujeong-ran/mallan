package com.mallan.yujeongran.icebreaking.liar_game.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.LiarGuessRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.LiarHostVerifyRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.LiarIsLiarRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.LiarVoteRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.service.LiarPlayerService;
import com.mallan.yujeongran.icebreaking.liar_game.service.LiarRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/liar/player")
@RequiredArgsConstructor
@Tag(name = "Liar Player API", description = "라이어 게임 플레이어 관련 API")
public class LiarPlayerController {

    private final LiarPlayerService liarPlayerService;
    private final LiarRoomService liarRoomService;

    @PostMapping("/{roomCode}/can-start")
    @Operation(summary = "게임 시작 가능 여부 확인 API", description = "4명 이상 12명 이하일 때 true 반환 (방장만 가능)")
    public ResponseEntity<CommonResponse<Boolean>> canStartGame(
            @PathVariable String roomCode,
            @RequestBody LiarHostVerifyRequestDto request
    ) {
        if (!liarPlayerService.isHost(roomCode, request.getHostId())) {
            throw new IllegalArgumentException("방장만 게임 시작 여부를 확인할 수 있습니다.");
        }
        boolean canStart = liarRoomService.canStartGame(roomCode);
        return ResponseEntity.ok(CommonResponse.success("게임 시작 가능 여부 조회 성공", canStart));
    }


    @PatchMapping("/{roomCode}/vote")
    @Operation(summary = "라이어 투표 API", description = "플레이어가 라이어라고 생각되는 플레이어에게 투표합니다.")
    public ResponseEntity<CommonResponse<Void>> vote(
            @PathVariable String roomCode,
            @RequestBody LiarVoteRequestDto request
    ) {
        liarRoomService.vote(roomCode, request.getPlayerId(), request.getTargetId());
        return ResponseEntity.ok(CommonResponse.success("투표 완료"));
    }

    @PatchMapping("/{roomCode}/guess")
    @Operation(summary = "라이어 정답 추측 API", description = "지목된 라이어가 실제 단어를 추측합니다.")
    public ResponseEntity<CommonResponse<Boolean>> liarGuess(
            @PathVariable String roomCode,
            @RequestBody LiarGuessRequestDto request
    ) {
        boolean isCorrect = liarRoomService.guessWord(roomCode, request.getPlayerId(), request.getWord());
        return ResponseEntity.ok(CommonResponse.success("라이어 정답 추측 결과 반환", isCorrect));
    }

    @PostMapping("/{roomCode}/is-liar")
    @Operation(summary = "사용자가 라이어인지 확인 API", description = "해당 플레이어가 라이어인지 여부를 반환합니다.")
    public ResponseEntity<CommonResponse<Boolean>> isLiar(
            @PathVariable String roomCode,
            @RequestBody LiarIsLiarRequestDto request
    ) {
        boolean result = liarPlayerService.isLiar(roomCode, request.getPlayerId());
        return ResponseEntity.ok(CommonResponse.success("라이어 여부 조회 성공", result));
    }

}
