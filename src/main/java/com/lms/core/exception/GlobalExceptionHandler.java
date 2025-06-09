package com.lms.core.exception;

import com.core.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 처리를 위한 핸들러 클래스
 * 애플리케이션에서 발생하는 예외들을 일관된 형식으로 처리합니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * BusinessException을 처리합니다.
     * HTTP 400 Bad Request 상태 코드와 함께 에러 응답을 반환합니다.
     *
     * @param e 발생한 BusinessException
     * @return 에러 응답을 포함한 ResponseEntity
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(e.getMessage()));
    }

    /**
     * 처리되지 않은 모든 예외를 처리합니다.
     * HTTP 500 Internal Server Error 상태 코드와 함께 에러 응답을 반환합니다.
     *
     * @param e 발생한 예외
     * @return 에러 응답을 포함한 ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal server error"));
    }
} 