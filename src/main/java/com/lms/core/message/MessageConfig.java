package com.lms.core.message;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * 메시지 설정 클래스
 * 
 * 주요 기능:
 * - 메시지 소스 설정
 * - 로케일 리졸버 설정
 * - 기본 인코딩 설정
 * 
 * @author core
 */
@Configuration
public class MessageConfig {

    /**
     * 메시지 소스를 설정합니다.
     * - 기본 인코딩: UTF-8
     * - 메시지 파일 위치: messages.properties
     *
     * @return MessageSource
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * 로케일 리졸버를 설정합니다.
     * - 기본 로케일: 한국어
     * - 세션 기반 로케일 관리
     *
     * @return LocaleResolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(Locale.KOREA);
        return resolver;
    }
} 