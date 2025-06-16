package com.mallan.yujeongran.icebreaking.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateLoginIdResponseDto {

    private String loginId;
    private String password;

}
