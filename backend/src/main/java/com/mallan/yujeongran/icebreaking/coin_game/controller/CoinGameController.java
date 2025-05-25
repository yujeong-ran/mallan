package com.mallan.yujeongran.icebreaking.coin_game.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.coin_game.dto.request.CoinSubmitAnswerRequestDto;
import com.mallan.yujeongran.icebreaking.coin_game.dto.request.CoinSubmitQuestionRequestDto;
import com.mallan.yujeongran.icebreaking.coin_game.dto.response.CoinFinalResultListResponseDto;
import com.mallan.yujeongran.icebreaking.coin_game.dto.response.CoinQuestionResultResponseDto;
import com.mallan.yujeongran.icebreaking.coin_game.service.CoinGameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coin/game")
@RequiredArgsConstructor
@Tag(name = "Coin Game API", description = "코인 진실 게임 진행 관련 API")
public class CoinGameController {

    private final CoinGameService coinGameService;
    private final RedisTemplate redisTemplate;

    @PostMapping("/{roomCode}/question")
    @Operation(summary = "질문 작성 API", description = "질문과 예측을 입력하여 제출합니다.")
    public ResponseEntity<CommonResponse<Void>> submitQuestion(
            @PathVariable String roomCode,
            @RequestBody CoinSubmitQuestionRequestDto request
    ) {
        coinGameService.submitQuestion(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("질문 제출 완료", null));
    }

    @PostMapping("/{roomCode}/answer")
    @Operation(summary = "답변 제출 API", description = "질문에 따른 답변을 제출합니다.(YES/NO)")
    public ResponseEntity<CommonResponse<Object>> submitAnswer(
            @PathVariable String roomCode,
            @RequestBody CoinSubmitAnswerRequestDto request
    ) {
        coinGameService.submitAnswer(roomCode, request);

        if (coinGameService.isAllAnswersSubmitted(roomCode)) {
            var result = coinGameService.completeQuestionAndReturnResult(roomCode);
            return ResponseEntity.ok(CommonResponse.success("답변 및 결과 처리 완료", result));
        }

        return ResponseEntity.ok(CommonResponse.success("답변 제출 완료", null));
    }

    @GetMapping("/{roomCode}/question/result")
    @Operation(summary = "질문 결과 확인 API", description = "모든 답변이 제출되면 결과를 반환합니다.")
    public ResponseEntity<CommonResponse<CoinQuestionResultResponseDto>> getQuestionResult(
            @PathVariable String roomCode
    ) {
        String resultKey = "coin:room:" + roomCode + ":lastResult";

        CoinQuestionResultResponseDto result = (CoinQuestionResultResponseDto) redisTemplate.opsForValue().get(resultKey);
        if (result == null) {
            return ResponseEntity.ok(CommonResponse.success("아직 결과가 준비되지 않았습니다.", null));
        }

        return ResponseEntity.ok(CommonResponse.success("질문 결과 반환 성공", result));
    }

    @GetMapping("/{roomCode}/result")
    @Operation(summary = "최종 결과 반환 API", description = "최종 결과를 반환합니다.")
    public ResponseEntity<CommonResponse<CoinFinalResultListResponseDto>> getFinalResults(
            @PathVariable String roomCode
    ) {
        var result = coinGameService.getFinalResults(roomCode);
        return ResponseEntity.ok(CommonResponse.success("최종 결과 반환 성공", result));
    }

    @PostMapping("/{roomCode}/restart")
    @Operation(summary = "게임 재시작 API", description = "질문/답변/점수를 초기화하고 게임을 다시 시작합니다.")
    public ResponseEntity<CommonResponse<Void>> restartGame(
            @PathVariable String roomCode
    ) {
        coinGameService.restartGame(roomCode);
        return ResponseEntity.ok(CommonResponse.success("게임이 재시작되었습니다.", null));
    }

    @PostMapping("/{roomCode}/end")
    @Operation(summary = "게임 종료 API", description = "방 및 관련 데이터를 모두 삭제합니다.")
    public ResponseEntity<CommonResponse<Void>> endGame(
            @PathVariable String roomCode
    ) {
        coinGameService.endGame(roomCode);
        return ResponseEntity.ok(CommonResponse.success("게임이 종료되었습니다.", null));
    }

}

