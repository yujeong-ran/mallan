package com.mallan.yujeongran.icebreaking.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class AdminLoginRequestDto {

    @Schema(description = "로그인 아이디", example = "mallanadmin")
    private String loginId;

    @Schema(description = "비밀번호", example = "mallanmallan")
    private String password;

}
