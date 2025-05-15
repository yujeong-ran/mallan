package com.mallan.yujeongran.icebreaking.liar_game.controller;

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
    public ResponseEntity<Map<String, String>> createPlayer(
            @RequestBody CreatePlayerRequestDto request
    ) {
        String nickname = request.getNickname();
        String playerId = liarRedisService.createPlayer(nickname);

        Map<String, String> response = new HashMap<>();
        response.put("Id", playerId);
        response.put("nickname", nickname);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roomCode}/can-start")
    @Operation(summary = "게임 시작 가능 여부 확인 API", description = "4명 이상 12명 이하일 때 true 반환")
    public ResponseEntity<Boolean> canStartGame(
            @PathVariable String roomCode
    ) {
        boolean canStart = liarRoomService.canStartGame(roomCode);
        return ResponseEntity.ok(canStart);
    }

}
