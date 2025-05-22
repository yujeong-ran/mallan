package com.mallan.yujeongran.icebreaking.balance_game.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.BalanceCreateQuestionRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceCreateQuestionResponseDto;
import com.mallan.yujeongran.icebreaking.balance_game.service.BalanceQuestionService;
import com.mallan.yujeongran.icebreaking.balance_game.service.BalanceTopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/balance/question")
@Tag(name = "Balance Question API", description = "밸런스 게임 질문 관련 API")
public class BalanceQuestionController {

    private final BalanceTopicService balanceTopicService;
    private final BalanceQuestionService balanceQuestionService;

    @PostMapping("/{topicId}/question")
    @Operation(summary = "질문 등록 API", description = "선택지를 포함한 질문을 해당 주제에 등록합니다.")
    public ResponseEntity<CommonResponse<BalanceCreateQuestionResponseDto>> createQuestion(
            @PathVariable Long topicId,
            @RequestBody BalanceCreateQuestionRequestDto request
    ) {
        BalanceCreateQuestionResponseDto response = balanceQuestionService.createQuestion(topicId, request);
        return ResponseEntity.ok(CommonResponse.success("질문 등록 성공!", response));
    }

    @GetMapping("/topic/{topicId}")
    @Operation(summary = "특정 주제의 질문 목록 조회 API", description = "특정 주제에 포함된 모든 질문을 조회합니다.")
    public ResponseEntity<CommonResponse<List<BalanceCreateQuestionResponseDto>>> getQuestionsByTopic(
            @PathVariable Long topicId
    ) {
        List<BalanceCreateQuestionResponseDto> response = balanceQuestionService.getQuestionsByTopicId(topicId);
        return ResponseEntity.ok(CommonResponse.success("질문 목록 조회 성공!", response));
    }

}
