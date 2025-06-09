package com.lms.core.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * 사용자 활동 로그를 저장하는 엔티티 클래스
 * 
 * 주요 기능:
 * - 사용자 활동 기록
 * - IP 주소 기록
 * - User-Agent 기록
 * - 타임스탬프 기록
 * 
 * @author core
 */
@Entity
@Table(name = "user_activity_logs")
@Getter
@Setter
public class UserActivityLog {

    /**
     * 로그 ID
     */
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 사용자명
     */
    @Column(nullable = false)
    private String username;

    /**
     * 수행한 작업
     */
    @Column(nullable = false)
    private String action;

    /**
     * 대상 리소스
     */
    @Column(nullable = false)
    private String resource;

    /**
     * 상세 정보
     */
    @Column
    private String details;

    /**
     * 발생 시간
     */
    @Column(nullable = false)
    private LocalDateTime timestamp;

    /**
     * IP 주소
     */
    @Column
    private String ipAddress;

    /**
     * User-Agent 정보
     */
    @Column
    private String userAgent;

    /**
     * 엔티티 저장 전에 타임스탬프를 설정합니다.
     */
    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }
}