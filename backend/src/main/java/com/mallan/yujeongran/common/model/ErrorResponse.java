package com.mallan.yujeongran.common.model;

import com.mallan.yujeongran.common.error.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

    private final int status;
    private final String message;

    public static ErrorResponse from(ErrorCode code){
        return ErrorResponse.builder()
                .status(code.getStatus().value())
                .message(code.getMessage())
                .build();
    }

}
