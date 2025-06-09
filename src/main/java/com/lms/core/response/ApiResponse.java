package com.lms.core.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * API 응답을 표준화하기 위한 공통 응답 클래스
 * 모든 API 응답은 이 클래스를 통해 일관된 형식으로 반환됩니다.
 *
 * @param <T> 응답 데이터의 타입
 */
@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> {
    /**
     * API 호출 성공 여부
     */
    private boolean success;
    
    /**
     * 응답 메시지
     */
    private String message;
    
    /**
     * 실제 응답 데이터
     */
    private T data;

    /**
     * 성공 응답을 생성합니다.
     *
     * @param data 응답 데이터
     * @return 성공 응답 객체
     */
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    /**
     * 메시지가 포함된 성공 응답을 생성합니다.
     *
     * @param message 응답 메시지
     * @param data 응답 데이터
     * @return 성공 응답 객체
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    /**
     * 에러 응답을 생성합니다.
     *
     * @param message 에러 메시지
     * @return 에러 응답 객체
     */
    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }
}