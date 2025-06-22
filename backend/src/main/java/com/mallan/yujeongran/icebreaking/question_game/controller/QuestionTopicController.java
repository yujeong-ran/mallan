package com.mallan.yujeongran.icebreaking.question_game.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.question_game.dto.request.QuestionCreateTopicRequestDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.request.QuestionDeleteTopicRequestDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.response.QuestionCreateTopicResponseDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.response.QuestionDeleteTopicResponseDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.response.QuestionTopicResponseDto;
import com.mallan.yujeongran.icebreaking.question_game.service.QuestionTopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question/topic")
@Tag(name = "Question Topic Controller", description = "오픈 퀘스천 게임 주제 관련 API")
public class QuestionTopicController {

    private final QuestionTopicService questionTopicService;

    @PostMapping
    @Operation(summary = "주제 등록 API", description = "새 주제를 등록합니다.")
    public ResponseEntity<CommonResponse<QuestionCreateTopicResponseDto>> createTopic(
            @RequestBody QuestionCreateTopicRequestDto request
    ) {
        QuestionCreateTopicResponseDto response = questionTopicService.createTopic(request);
        return ResponseEntity.ok(CommonResponse.success("주제 등록 성공!", response));
    }

    @GetMapping("/all")
    @Operation(summary = "모든 주제 출력 API", description = "오픈 퀘스천 게임에 등록된 모든 주제를 출력합니다.")
    public ResponseEntity<List<QuestionTopicResponseDto>> getAllTopic() {
        return ResponseEntity.ok(questionTopicService.getAllTopics());
    }

    @DeleteMapping("/delete")
    @Operation(summary = "주제 삭제 API", description = "주제 아이디를 이용해서 주제를 삭제합니다.")
    public ResponseEntity<CommonResponse<QuestionDeleteTopicResponseDto>> deleteTopic(
            @RequestBody QuestionDeleteTopicRequestDto request
    ) {
        QuestionDeleteTopicResponseDto response = questionTopicService.deleteTopic(request);
        return ResponseEntity.ok(CommonResponse.success("주제 삭제 성공", response));
    }

}
