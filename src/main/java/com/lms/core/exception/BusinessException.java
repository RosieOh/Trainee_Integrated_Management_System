package com.lms.core.exception;

import lombok.Getter;

/**
 * 비즈니스 로직에서 발생하는 예외를 처리하기 위한 커스텀 예외 클래스
 * 비즈니스 규칙 위반이나 유효성 검사 실패 등의 상황에서 사용됩니다.
 */
@Getter
public class BusinessException extends RuntimeException {
    /**
     * 에러 코드
     * 기본값은 "BUSINESS_ERROR"이며, 생성자를 통해 다른 코드로 변경 가능합니다.
     */
    private final String code;

    /**
     * 기본 에러 코드를 사용하는 예외를 생성합니다.
     *
     * @param message 에러 메시지
     */
    public BusinessException(String message) {
        super(message);
        this.code = "BUSINESS_ERROR";
    }

    /**
     * 커스텀 에러 코드를 사용하는 예외를 생성합니다.
     *
     * @param code 에러 코드
     * @param message 에러 메시지
     */
    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }
} 