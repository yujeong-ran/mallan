package com.mallan.yujeongran.icebreaking.question_game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionPlayerRoleResponseDto {

    /*
    * "questioner", "answerer", "none"
    * 으로 구분해서 현재 플레이어가 어떤 상태의 역할인지 확인하려고 함.
    * 역할을 Enum클래스로 넘겨서 받아야하나 고민
    * */
    private String role;

}
