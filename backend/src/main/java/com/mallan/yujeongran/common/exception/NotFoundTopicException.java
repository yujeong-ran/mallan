package com.mallan.yujeongran.common.exception;


import com.mallan.yujeongran.common.error.ErrorCode;

public class NotFoundTopicException extends MallanCustomException {

    public NotFoundTopicException() {
        super(ErrorCode.NOT_FOUND_TOPIC);
    }

}
