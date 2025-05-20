package com.mallan.yujeongran.icebreaking.balance_game.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.StartBalanceGameRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.SubmitBalanceAnswerRequestDto;
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
            @RequestBody StartBalanceGameRequestDto request
    ) {
        balanceGameService.startGame(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("게임이 시작되었습니다!"));
    }

    @PostMapping("/{roomCode}/question/{questionId}/submit")
    @Operation(summary = "답변 제출 API", description = "현재 질문에 대해 사용자의 선택을 제출합니다.")
    public ResponseEntity<CommonResponse<Void>> submitAnswer(
            @PathVariable String roomCode,
            @PathVariable Long questionId,
            @RequestBody SubmitBalanceAnswerRequestDto request
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

    @GetMapping("/{roomCode}/final-result")
    @Operation(summary = "최종 결과 반환 API", description = "모든 문항에 대한 투표 결과를 반환합니다.")
    public ResponseEntity<CommonResponse<List<BalanceFinalResultResponseDto>>> getFinalResult(
            @PathVariable String roomCode
    ) {
        List<BalanceFinalResultResponseDto> result = balanceGameService.getFinalResult(roomCode);
        return ResponseEntity.ok(CommonResponse.success("최종 결과 조회 성공!", result));
    }

    @PatchMapping("/{roomCode}/restart")
    @Operation(summary = "게임 재시작 API", description = "현재 방을 재사용해 새로운 게임을 시작합니다.")
    public ResponseEntity<CommonResponse<Void>> restartGame(@PathVariable String roomCode) {
        balanceGameService.restartGame(roomCode);
        return ResponseEntity.ok(CommonResponse.success("게임이 초기화되었습니다!"));
    }

    @DeleteMapping("/{roomCode}/delete")
    @Operation(summary = "게임 종료 API", description = "방을 완전히 삭제합니다.")
    public ResponseEntity<CommonResponse<Void>> endGame(
            @PathVariable String roomCode
    ) {
        balanceGameService.deleteGame(roomCode);
        return ResponseEntity.ok(CommonResponse.success("방이 삭제되었습니다."));
    }

}
