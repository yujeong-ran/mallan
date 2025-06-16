package com.mallan.yujeongran.icebreaking.admin.dto.request;

import lombok.Getter;

@Getter
public class DeleteLoginIdRequestDto {

    private String secretKey;
    private String loginId;
    private String password;

}
