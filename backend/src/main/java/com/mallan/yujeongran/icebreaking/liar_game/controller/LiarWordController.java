package com.mallan.yujeongran.icebreaking.liar_game.controller;

import com.mallan.yujeongran.icebreaking.liar_game.dto.response.LiarWordResponseDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.CreateLiarWordRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.service.LiarWordService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/liar/word")
@RequiredArgsConstructor
@Tag(name = "Liar Word API", description = "라이어게임 단어 관련 API")
public class LiarWordController {

    private final LiarWordService liarWordService;

    @PostMapping
    @Operation(summary = "단어 등록 API", description = "주제 선정 후 단어를 추가합니다.")
    public ResponseEntity<LiarWordResponseDto> createWord(
            @RequestBody CreateLiarWordRequestDto requestDto
    ) {
        return ResponseEntity.ok(liarWordService.createWord(requestDto));
    }

    @GetMapping("/{topicId}")
    @Operation(summary = "단어 주출 API", description = "해당 주제에 등록된 단어들을 추출합니다.")
    public ResponseEntity<List<LiarWordResponseDto>> getWordsByTopic(@PathVariable Long topicId) {
        return ResponseEntity.ok(liarWordService.getWordsByTopic(topicId));
    }

}
