package com.mallan.yujeongran.icebreaking.question_game.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.question_game.dto.request.QuestionEndGameRequestDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.request.QuestionRestartGameRequestDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.request.QuestionSelectAnswererRequestDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.request.QuestionSubmitAnswerRequestDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.response.QuestionCurrentQuestionStateResponseDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.response.QuestionDrawResponseDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.response.QuestionPlayerRoleResponseDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.response.QuestionSelectablePlayerListResponseDto;
import com.mallan.yujeongran.icebreaking.question_game.service.QuestionGameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question/game")
@RequiredArgsConstructor
@Tag(name = "Question Game API", description = "오픈 퀘스천 게임 진행 관련 API")
public class QuestionGameController {

    private final QuestionGameService questionGameService;

    @PostMapping("/{roomCode}/start")
    @Operation(summary = "게임 시작 API", description = "주제 ID와 문제 수에 따라 게임을 시작합니다.")
    public ResponseEntity<CommonResponse<Void>> startGame(
            @PathVariable String roomCode,
            @RequestParam int topicId,
            @RequestParam int questionCount
    ) {
        questionGameService.startGame(roomCode, topicId, questionCount);
        return ResponseEntity.ok(CommonResponse.success("게임 시작 완료!", null));
    }

    @GetMapping("/{roomCode}/draw")
    @Operation(summary = "질문 뽑기 API", description = "랜덤 질문을 하나 뽑습니다.")
    public ResponseEntity<CommonResponse<QuestionDrawResponseDto>> drawQuestion(
            @PathVariable String roomCode
    ) {
        QuestionDrawResponseDto question = questionGameService.drawQuestion(roomCode);
        return ResponseEntity.ok(CommonResponse.success("질문 뽑기 완료", question));
    }

    @GetMapping("/{roomCode}/players/selectable")
    @Operation(summary = "답변자 선택 가능한 플레이어 리스트 API", description = "질문자를 제외한 플레이어 목록을 반환합니다.")
    public ResponseEntity<CommonResponse<QuestionSelectablePlayerListResponseDto>> getSelectablePlayers(
            @PathVariable String roomCode,
            @RequestParam String questionerId
    ) {
        QuestionSelectablePlayerListResponseDto response = questionGameService.getSelectablePlayers(roomCode, questionerId);
        return ResponseEntity.ok(CommonResponse.success("선택 가능한 플레이어 조회 성공", response));
    }

    @PostMapping("/{roomCode}/select")
    @Operation(summary = "답변자 지정 API", description = "질문자가 답변자를 선택합니다.")
    public ResponseEntity<CommonResponse<Void>> selectAnswerer(
            @PathVariable String roomCode,
            @RequestBody QuestionSelectAnswererRequestDto request
    ) {
        questionGameService.selectAnswerer(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("답변자 선택 완료", null));
    }

    @PostMapping("/{roomCode}/answer")
    @Operation(summary = "답변 제출 API", description = "지목된 사용자가 질문에 답변을 완료합니다.")
    public ResponseEntity<CommonResponse<Void>> submitAnswer(
            @PathVariable String roomCode,
            @RequestBody QuestionSubmitAnswerRequestDto request
    ) {
        questionGameService.submitAnswer(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("답변 완료", null));
    }

    @GetMapping("/{roomCode}/finished")
    @Operation(summary = "게임 종료 여부 확인 API", description = "남은 질문이 있는지 확인합니다.")
    public ResponseEntity<CommonResponse<Boolean>> isGameFinished(
            @PathVariable String roomCode
    ) {
        boolean result = questionGameService.isGameFinished(roomCode);
        return ResponseEntity.ok(CommonResponse.success("게임 종료 여부 확인", result));
    }

    @PostMapping("/{roomCode}/restart")
    @Operation(summary = "게임 재시작 API", description = "기존 게임 데이터를 초기화하고 새 게임을 시작합니다.")
    public ResponseEntity<CommonResponse<Void>> restartGame(
            @PathVariable String roomCode,
            @RequestParam int topicId,
            @RequestParam int questionCount,
            @RequestBody QuestionRestartGameRequestDto request
    ) {
        questionGameService.restartGame(roomCode, request, topicId, questionCount);
        return ResponseEntity.ok(CommonResponse.success("게임 재시작 완료!", null));
    }

    @DeleteMapping("/{roomCode}/end")
    @Operation(summary = "게임 종료 API", description = "게임을 완전히 종료하고 모든 관련 데이터를 삭제합니다.")
    public ResponseEntity<CommonResponse<Void>> endGame(
            @PathVariable String roomCode,
            @RequestBody QuestionEndGameRequestDto request
    ) {
        questionGameService.endGame(roomCode, request);
        return ResponseEntity.ok(CommonResponse.success("게임 종료 완료", null));
    }

    @GetMapping("/{roomCode}/current")
    @Operation(summary = "현재 질문 상태 조회 API", description = "현재 질문자, 답변자, 질문 내용을 조회합니다.")
    public ResponseEntity<CommonResponse<QuestionCurrentQuestionStateResponseDto>> getCurrentQuestionState(
            @PathVariable String roomCode
    ) {
        QuestionCurrentQuestionStateResponseDto response = questionGameService.getCurrentQuestionState(roomCode);
        return ResponseEntity.ok(CommonResponse.success("현재 질문 상태 조회 성공", response));
    }

    @GetMapping("/{roomCode}/role")
    @Operation(summary = "플레이어 역할 조회 API", description = "플레이어가 질문자인지 답변자인지 확인합니다.")
    public ResponseEntity<CommonResponse<QuestionPlayerRoleResponseDto>> getPlayerRole(
            @PathVariable String roomCode,
            @RequestParam String playerId
    ) {
        QuestionPlayerRoleResponseDto response = questionGameService.getPlayerRole(roomCode, playerId);
        return ResponseEntity.ok(CommonResponse.success("플레이어 역할 조회 성공", response));
    }

}
