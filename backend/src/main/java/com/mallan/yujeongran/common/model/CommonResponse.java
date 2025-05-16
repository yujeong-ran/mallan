package com.mallan.yujeongran.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommonResponse<T> {

    private int status;
    private String message;
    private T data;

    public static <T> CommonResponse<T> success(String message, T data) {
        return CommonResponse.<T>builder()
                .status(200)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> CommonResponse<T> success(String message) {
        return success(message, null);
    }

    public static <T> CommonResponse<T> failure(String message) {
        return CommonResponse.<T>builder()
                .status(400)
                .message(message)
                .data(null)
                .build();
    }

}
