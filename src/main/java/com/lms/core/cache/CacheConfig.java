package com.lms.core.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 캐시 설정 클래스
 * 
 * 주요 기능:
 * - 캐시 매니저 설정
 * - 캐시 이름 정의
 * - 캐시 활성화
 * 
 * @author core
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * ConcurrentMap 기반의 캐시 매니저를 생성합니다.
     * 기본 캐시 이름:
     * - users: 사용자 정보 캐시
     * - products: 상품 정보 캐시
     * - categories: 카테고리 정보 캐시
     *
     * @return CacheManager
     */
    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        cacheManager.setCacheNames(java.util.Arrays.asList(
            "users",
            "products",
            "categories"
        ));
        return cacheManager;
    }
} 