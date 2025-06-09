package com.lms.core.message;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * 메시지 유틸리티 클래스
 * 
 * 주요 기능:
 * - 메시지 조회
 * - 메시지 포맷팅
 * - 기본 메시지 처리
 * 
 * @author core
 */
@Component
public class MessageUtil {

    private final MessageSource messageSource;

    /**
     * MessageSource를 주입받아 초기화합니다.
     *
     * @param messageSource 메시지 소스
     */
    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * 메시지 코드에 해당하는 메시지를 조회합니다.
     *
     * @param code 메시지 코드
     * @return 메시지
     */
    public String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    /**
     * 메시지 코드와 인자를 사용하여 메시지를 포맷팅합니다.
     *
     * @param code 메시지 코드
     * @param args 메시지 인자
     * @return 포맷팅된 메시지
     */
    public String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    /**
     * 메시지 코드에 해당하는 메시지를 조회하고, 없을 경우 기본 메시지를 반환합니다.
     *
     * @param code 메시지 코드
     * @param defaultMessage 기본 메시지
     * @return 메시지 또는 기본 메시지
     */
    public String getMessage(String code, String defaultMessage) {
        return messageSource.getMessage(code, null, defaultMessage, LocaleContextHolder.getLocale());
    }

    /**
     * 메시지 코드와 인자를 사용하여 메시지를 포맷팅하고, 없을 경우 기본 메시지를 반환합니다.
     *
     * @param code 메시지 코드
     * @param args 메시지 인자
     * @param defaultMessage 기본 메시지
     * @return 포맷팅된 메시지 또는 기본 메시지
     */
    public String getMessage(String code, Object[] args, String defaultMessage) {
        return messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }
} 