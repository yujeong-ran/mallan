package com.mallan.yujeongran.icebreaking.liar_game.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.CreatePlayerRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.service.LiarRedisService;
import com.mallan.yujeongran.icebreaking.liar_game.service.LiarRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/liar/player")
@RequiredArgsConstructor
@Tag(name = "Liar Player API", description = "라이어 게임 플레이어 관련 API")
public class LiarPlayerController {

    private final LiarRedisService liarRedisService;
    private final LiarRoomService liarRoomService;

    @PostMapping
    @Operation(summary = "플레이어 생성 API", description = "닉네임을 입력받아 플레이어를 생성합니다.")
    public ResponseEntity<CommonResponse<Map<String, String>>> createPlayer(@RequestBody CreatePlayerRequestDto request) {
        String nickname = request.getNickname();
        String playerId = liarRedisService.createPlayer(nickname);

        Map<String, String> response = new HashMap<>();
        response.put("id", playerId);
        response.put("nickname", nickname);

        return ResponseEntity.ok(CommonResponse.success("플레이어 생성 성공", response));
    }

    @GetMapping("/{roomCode}/can-start")
    @Operation(summary = "게임 시작 가능 여부 확인 API", description = "4명 이상 12명 이하일 때 true 반환")
    public ResponseEntity<CommonResponse<Boolean>> canStartGame(
            @PathVariable String roomCode
    ) {
        boolean canStart = liarRoomService.canStartGame(roomCode);
        return ResponseEntity.ok(CommonResponse.success("게임 시작 가능 여부 조회 성공", canStart));
    }

    @PatchMapping("/{roomCode}/vote")
    @Operation(summary = "라이어 투표 API", description = "플레이어가 라이어라고 생각되는 플레이어에게 투표합니다.")
    public ResponseEntity<CommonResponse<Void>> vote(
            @PathVariable String roomCode,
            @RequestParam("voterId") String voterId,
            @RequestParam("targetId") String targetId
    ) {
        liarRoomService.vote(roomCode, voterId, targetId);
        return ResponseEntity.ok(CommonResponse.success("투표 완료"));
    }

    @PatchMapping("/{roomCode}/guess")
    @Operation(summary = "라이어 정답 추측 API", description = "지목된 라이어가 실제 단어를 추측합니다.")
    public ResponseEntity<CommonResponse<Boolean>> liarGuess(
            @PathVariable String roomCode,
            @RequestParam("word") String guess
    ) {
        boolean isCorrect = liarRoomService.guessWord(roomCode, guess);
        return ResponseEntity.ok(CommonResponse.success("라이어 정답 추측 결과 반환", isCorrect));
    }

    @GetMapping("/{roomCode}/is-liar")
    @Operation(summary = "사용자가 라이어인지 확인 API", description = "해당 플레이어가 라이어인지 여부를 반환합니다.")
    public ResponseEntity<CommonResponse<Boolean>> isLiar(
            @PathVariable String roomCode,
            @RequestParam("playerId") String playerId
    ) {
        boolean isLiar = liarRedisService.isLiar(roomCode, playerId);
        return ResponseEntity.ok(CommonResponse.success("라이어 여부 조회 성공", isLiar));
    }

}
