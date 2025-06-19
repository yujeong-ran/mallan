package com.mallan.yujeongran.icebreaking.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminLoginResponseDto {

    private String loginId;
    private String message;

}
