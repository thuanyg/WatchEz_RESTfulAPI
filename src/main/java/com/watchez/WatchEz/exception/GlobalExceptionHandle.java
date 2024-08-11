package com.watchez.WatchEz.exception;

import com.watchez.WatchEz.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<Object>> handleRuntimeException(AppException e) {
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .statusCode(e.getErrorCode().getCode())
                .message(e.getErrorCode().getMessage())
                .data(null)
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }
}
