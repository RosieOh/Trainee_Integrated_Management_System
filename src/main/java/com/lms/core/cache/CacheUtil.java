package com.lms.core.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

/**
 * 캐시 유틸리티 클래스
 * 
 * 주요 기능:
 * - 캐시 데이터 저장
 * - 캐시 데이터 조회
 * - 캐시 데이터 삭제
 * - 캐시 초기화
 * 
 * @author core
 */
@Component
public class CacheUtil {
    
    private final CacheManager cacheManager;

    /**
     * CacheManager를 주입받아 초기화합니다.
     *
     * @param cacheManager 캐시 매니저
     */
    public CacheUtil(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * 캐시에 데이터를 저장합니다.
     *
     * @param cacheName 캐시 이름
     * @param key 캐시 키
     * @param value 저장할 값
     */
    public void put(String cacheName, Object key, Object value) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(key, value);
        }
    }

    /**
     * 캐시에서 데이터를 조회합니다.
     *
     * @param <T> 반환할 데이터 타입
     * @param cacheName 캐시 이름
     * @param key 캐시 키
     * @param type 반환할 데이터 타입
     * @return 캐시된 데이터 또는 null
     */
    public <T> T get(String cacheName, Object key, Class<T> type) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(key);
            if (valueWrapper != null) {
                return type.cast(valueWrapper.get());
            }
        }
        return null;
    }

    /**
     * 캐시에서 특정 키의 데이터를 삭제합니다.
     *
     * @param cacheName 캐시 이름
     * @param key 삭제할 캐시 키
     */
    public void evict(String cacheName, Object key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
        }
    }

    /**
     * 특정 캐시의 모든 데이터를 삭제합니다.
     *
     * @param cacheName 캐시 이름
     */
    public void clear(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }

    /**
     * 모든 캐시의 데이터를 삭제합니다.
     */
    public void clearAll() {
        cacheManager.getCacheNames().forEach(this::clear);
    }
} 