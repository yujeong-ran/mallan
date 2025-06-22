package com.mallan.yujeongran.icebreaking.question_game.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.question_game.dto.request.QuestionCreateQuestionRequestDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.request.QuestionDeleteQuestionRequestDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.request.QuestionGetQuestionByTopicRequestDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.response.QuestionCreateQuestionResponseDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.response.QuestionDeleteQuestionResponseDto;
import com.mallan.yujeongran.icebreaking.question_game.dto.response.QuestionQuestionResponseDto;
import com.mallan.yujeongran.icebreaking.question_game.service.QuestionQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question/question")
@Tag(name = "Question Question Controller", description = "오픈 퀘스천 게임 질문 관련 API")
public class QuestionQuestionController {

    private final QuestionQuestionService questionQuestionService;

    @PostMapping
    @Operation(summary = "질문 등록 API", description = "주제를 정하고 해당 주제와 관련된 질문을 등록합니다.")
    public ResponseEntity<CommonResponse<QuestionCreateQuestionResponseDto>> createQuestion(
            @RequestBody QuestionCreateQuestionRequestDto request
    ) {
        QuestionCreateQuestionResponseDto response = questionQuestionService.createQuestion(request);
        return ResponseEntity.ok(CommonResponse.success("질문 등록 성공", response));
    }

    @PostMapping("/list")
    @Operation(summary = "질문 조회 API", description = "특정 주제에 속한 질문들을 조회합니다.")
    public ResponseEntity<CommonResponse<List<QuestionQuestionResponseDto>>> getQuestionsByTopic(
            @RequestBody QuestionGetQuestionByTopicRequestDto request
    ) {
        List<QuestionQuestionResponseDto> responseList = questionQuestionService.getQuestionByTopicId(request);
        return ResponseEntity.ok(CommonResponse.success("질문 조회 성공!", responseList));
    }


    @DeleteMapping("/delete")
    @Operation(summary = "질문 삭제 API", description = "질문 ID를 통해 해당 질문을 삭제합니다.")
    public ResponseEntity<CommonResponse<QuestionDeleteQuestionResponseDto>> deleteQuestion(
            @RequestBody QuestionDeleteQuestionRequestDto request
    ) {
        QuestionDeleteQuestionResponseDto response = questionQuestionService.deleteQuestion(request);
        return ResponseEntity.ok(CommonResponse.success("질문 삭제 성공!", response));
    }

}
