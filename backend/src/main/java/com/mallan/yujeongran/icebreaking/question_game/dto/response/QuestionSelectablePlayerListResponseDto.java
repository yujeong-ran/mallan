package com.mallan.yujeongran.icebreaking.question_game.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuestionSelectablePlayerListResponseDto {

    private List<QuestionSelectablePlayerResponseDto> players;

}
