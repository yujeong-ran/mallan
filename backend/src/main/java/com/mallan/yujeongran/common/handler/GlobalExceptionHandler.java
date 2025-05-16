package com.mallan.yujeongran.common.handler;

import com.mallan.yujeongran.common.exception.DuplicateJoinException;
import com.mallan.yujeongran.common.exception.MallanCustomException;
import com.mallan.yujeongran.common.error.ErrorCode;
import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.common.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MallanCustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(MallanCustomException ex) {
        return ResponseEntity
                .status(ex.getErrorCode().getStatus())
                .body(ErrorResponse.from(ex.getErrorCode()));
    }

    @ExceptionHandler(DuplicateJoinException.class)
    public ResponseEntity<CommonResponse<Void>> handleDuplicateJoin(DuplicateJoinException ex) {
        return ResponseEntity.badRequest().body(CommonResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> habdleUnhandledException(Exception ex) {
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("error", "Bad Request");
        error.put("message", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
