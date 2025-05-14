package com.mallan.yujeongran.common.exception;

import com.mallan.yujeongran.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class MallanCustomException extends RuntimeException{

    private final ErrorCode errorCode;

    public MallanCustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
