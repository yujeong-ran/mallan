package com.mallan.yujeongran.icebreaking.balance_game.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.CreateBalanceTopicRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceTopicResponseDto;
import com.mallan.yujeongran.icebreaking.balance_game.service.BalanceTopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/balance/topic")
@Tag(name = "Balance Topic API", description = "밸런스 게임 주제 관련 API")
public class BalanceTopicController {

    private final BalanceTopicService balanceTopicService;

    @PostMapping
    @Operation(summary = "주제 생성 API", description = "새로운 밸런스 게임 주제를 생성합니다.")
    public ResponseEntity<CommonResponse<BalanceTopicResponseDto>> createTopic(
            @RequestBody CreateBalanceTopicRequestDto request
    ) {
        BalanceTopicResponseDto response = balanceTopicService.createTopic(request);
        return ResponseEntity.ok(CommonResponse.success("주제 생성 성공!", response));
    }

    @GetMapping
    @Operation(summary = "주제 목록 조회 API", description = "모든 밸런스 게임 주제를 조회합니다.")
    public ResponseEntity<CommonResponse<List<BalanceTopicResponseDto>>> getAllTopics() {
        List<BalanceTopicResponseDto> response = balanceTopicService.getAllTopics();
        return ResponseEntity.ok(CommonResponse.success("주제 목록 조회 성공!", response));
    }



}
