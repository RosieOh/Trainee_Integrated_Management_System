package com.lms.domain.student.entity;

import com.lms.global.cosntant.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student extends BaseEntity {

    @Id
    private Long no;

    @Column(nullable = true)
    private String cert;            //자격증

    @Column(nullable = true)
    private String exp;             //경력

    @Column(nullable = true)
    private String stack;           //기술스택

    @Column(nullable = true)
    private String OA;              // Word/PPT/Excel

    @Column(nullable = true)
    private String content;         // 상담내용

    @Column(nullable = true)
    private String satis;           // 전반적 만족도

    @Column(nullable = true)
    private String edu_data;        // 학습자료

    @Column(nullable = true)
    private String instructor;      // 담당 강사

    @Column(nullable = true)
    private String task;            // 과제 및 피드백

    @Column(nullable = true)
    private String env;             // 학습환경 및 장비

    @Column(nullable = true)
    private String goal;            // 최종목표

    @Column(nullable = true)
    private String doc;             // 입사지원 서류관련 의견

    @Column(nullable = true)
    private String personal;        // 성격 및 태도

    @Column(nullable = true)
    private String opinion;         // 종합 의견

    @Column(nullable = true)
    private String etc;             // 기타

}
