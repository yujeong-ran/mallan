package com.mallan.yujeongran.icebreaking.question_game.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class QuestionCreateRoomRequestDto {

    @Schema(description = "닉네임", example = "Noah")
    private String Nickname;

    @Schema(description = "프로필 이미지", example = "avatar_1_140x140.png")
    private String profileImage;

}
