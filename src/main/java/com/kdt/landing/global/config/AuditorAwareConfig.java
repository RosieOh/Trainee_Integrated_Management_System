package com.kdt.landing.global.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
public class AuditorAwareConfig implements AuditorAware<String> {

    // 현재 사용자의 ID를 반환하는 메서드
    @Override
    public Optional<String> getCurrentAuditor() {
        // Spring Security의 Authentication을 사용하여 현재 인증된 사용자 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = "";

        // 인증 정보가 존재하면 사용자의 ID를 가져옴
        if (authentication != null) {
            userId = authentication.getName();
        }

        return Optional.of(userId);
    }
}
