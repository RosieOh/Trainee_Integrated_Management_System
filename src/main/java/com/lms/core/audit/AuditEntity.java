package com.lms.core.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 엔티티의 감사 정보를 관리하는 추상 클래스
 * 
 * 주요 기능:
 * - 생성 시간 추적
 * - 수정 시간 추적
 * - 생성자 추적
 * - 수정자 추적
 * 
 * @author core
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditEntity {

    /**
     * 엔티티 생성 시간
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 엔티티 수정 시간
     */
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 엔티티 생성자
     */
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;

    /**
     * 엔티티 수정자
     */
    @LastModifiedBy
    @Column(nullable = false)
    private String updatedBy;
} 