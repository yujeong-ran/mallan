package com.mallan.yujeongran.icebreaking.liar_game.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LiarGameResultResponseDto {

    private String winnerType;
    private String realWord;
    private String liarGuess;
    private List<String> votes;

}

