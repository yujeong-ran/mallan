package com.mallan.yujeongran.icebreaking.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CreateLoginIdRequestDto {

    @Schema(description = "ADMIN_SECRET_KEY")
    private String secretKey;

    @Schema(description = "로그인 아이디", example = "MallanAdmin")
    private String loginId;

    @Schema(description = "비밀번호", example = "mallan-mallan")
    private String password;

}
