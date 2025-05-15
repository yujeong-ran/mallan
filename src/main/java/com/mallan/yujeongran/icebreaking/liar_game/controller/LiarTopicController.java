package com.mallan.yujeongran.icebreaking.liar_game.controller;

import com.mallan.yujeongran.icebreaking.liar_game.dto.response.LiarTopicResponseDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.CreateLiarTopicRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.service.LiarTopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/liar/topics")
@RequiredArgsConstructor
@Tag(name = "Liar Topic API", description = "라이어게임 주제 관련 API")
public class LiarTopicController {

    private final LiarTopicService liarTopicService;

    @PostMapping
    @Operation(summary = "주제 등록 API", description = "라이어게임의 주제를 등록합니다.")
    public ResponseEntity<LiarTopicResponseDto> createTopic(
            @RequestBody CreateLiarTopicRequestDto requestDto
    ) {
        return ResponseEntity.ok(liarTopicService.createTopic(requestDto));
    }

    @GetMapping
    @Operation(summary = "주제 추출 API", description = "라이어게임의 모든 주제를 추출합니다.")
    public ResponseEntity<List<LiarTopicResponseDto>> getAllTopic() {
        return ResponseEntity.ok(liarTopicService.getAllTopics());
    }

}
