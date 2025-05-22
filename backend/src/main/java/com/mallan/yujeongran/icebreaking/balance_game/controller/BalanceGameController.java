package com.mallan.yujeongran.icebreaking.balance_game.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.*;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceCreateIngameQuestionResponseDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceFinalResultResponseDto;
import com.mallan.yujeongran.icebreaking.balance_game.service.BalanceGameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/balance/game")
@Tag(name = "Balance Game API", description = "밸런스 게임 진행 관련 API")
public class BalanceGameController {

    private final BalanceGameService balanceGameService;

    @PostMapping("/{roomCode}/start")
    @Operation(summary = "게임 시작 API", description = "방장이 게임을 시작합니다.")
    public ResponseEntity<CommonResponse<Void>> startGame(
            @PathVariable String roomCode,
            @RequestBody BalanceStartGameRequestDto request
    ) {
        balanceGameService.startGame(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("게임이 시작되었습니다!"));
    }

    @PostMapping("/{roomCode}/create-question")
    @Operation(
            summary = "문제 생성 API",
            description = "게임 시작 시 셔플된 문제 리스트 중 현재 순서의 문제를 제공하고, 다음 인덱스를 자동 갱신합니다. 중복 없이 한 문제씩 제공됩니다."
    )
    public ResponseEntity<CommonResponse<BalanceCreateIngameQuestionResponseDto>> createIngameQuestion(
            @PathVariable String roomCode,
            @RequestBody BalanceCreateIngameQuestionRequestDto request
    ) {
        BalanceCreateIngameQuestionResponseDto response = balanceGameService.createIngameQuestion(roomCode, request);

        if (response == null) {
            return ResponseEntity.ok(CommonResponse.failure("모든 문제가 끝났습니다!"));
        }

        return ResponseEntity.ok(CommonResponse.success("문제 생성 성공!", response));
    }


    @PostMapping("/{roomCode}/question/{questionId}/submit")
    @Operation(summary = "답변 제출 API", description = "현재 질문에 대해 사용자의 선택을 제출합니다.")
    public ResponseEntity<CommonResponse<Void>> submitAnswer(
            @PathVariable String roomCode,
            @PathVariable Long questionId,
            @RequestBody BalanceSubmitAnswerRequestDto request
    ) {
        balanceGameService.submitAnswer(roomCode, questionId, request);
        return ResponseEntity.ok(CommonResponse.success("답변 제출 완료!"));
    }

    @GetMapping("/{roomCode}/question/{questionId}/result")
    @Operation(summary = "질문 결과 확인 API", description = "해당 질문에 대한 선택지별 결과를 반환합니다.")
    public ResponseEntity<CommonResponse<Map<String, Integer>>> getQuestionResult(
            @PathVariable String roomCode,
            @PathVariable Long questionId
    ) {
        Map<String, Integer> result = balanceGameService.getQuestionResult(roomCode, questionId);
        return ResponseEntity.ok(CommonResponse.success("결과 조회 완료!", result));
    }

    @PostMapping("/{roomCode}/finalize")
    @Operation(summary = "최종 결과 저장 API", description = "마지막 문제 이후에 해당 API 최종 결과를 저장합니다.")
    public ResponseEntity<CommonResponse<Void>> finalizeGame(
            @PathVariable String roomCode
    ) {
        balanceGameService.saveFinalResult(roomCode);
        return ResponseEntity.ok(CommonResponse.success("최종 결과 저장 완료!"));
    }

    @GetMapping("/{roomCode}/final-result/{playerId}")
    @Operation(summary = "최종 결과 반환 API", description = "내 기준으로 각 플레이어와의 일치율을 반환합니다.")
    public ResponseEntity<CommonResponse<List<BalanceFinalResultResponseDto>>> getSimilarityResult(
            @PathVariable String roomCode,
            @PathVariable String playerId
    ) {
        List<BalanceFinalResultResponseDto> result = balanceGameService.calculateSimilarity(roomCode, playerId);
        return ResponseEntity.ok(CommonResponse.success("최종 결과 조회 성공!", result));
    }
    
    @PatchMapping("/{roomCode}/restart")
    @Operation(summary = "게임 재시작 API", description = "현재 방을 재사용해 새로운 게임을 시작합니다.")
    public ResponseEntity<CommonResponse<Void>> restartGame(
            @PathVariable String roomCode,
            @RequestBody BalanceRestartGameRequestDto request
    ) {
        balanceGameService.restartGame(roomCode, request.getPlayerId());
        return ResponseEntity.ok(CommonResponse.success("게임이 초기화되었습니다!"));
    }

    @PatchMapping("/{roomCode}/delete")
    @Operation(summary = "게임 종료 API", description = "방을 완전히 삭제합니다.")
    public ResponseEntity<CommonResponse<Void>> endGame(
            @PathVariable String roomCode,
            @RequestBody BalanceDeleteGameRequestDto request
    ) {
        balanceGameService.deleteGame(roomCode, request.getPlayerId());
        return ResponseEntity.ok(CommonResponse.success("방이 삭제되었습니다."));
    }


}
