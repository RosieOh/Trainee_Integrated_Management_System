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

    //    학생들 입력
    @Column(nullable = true)
    private String stack;           //기술스택

    @Column(nullable = true)
    private String cert;            //자격증

    @Column(nullable = true)
    private String resume;            //이력서

    @Column(nullable = true)
    private String univ;            //대학교

    @Column(nullable = true)
    private String major;            //학과

    @Column(nullable = true)
    private String job_program;            //국취참여여부

    @Column(nullable = true)
    private String fund;            //지원센터

    @Column(nullable = true)
    private String picture;            //사진

    @Column(nullable = true)
    private String hope;            //희망 진로분야

    @Column(nullable = true)
    private String portfolio;            //포트폴리오


    //    매니저 입력


    @Column(nullable = true)
    private String exp;             //경력

    @Column(nullable = true)
    private String OA;              // Word/PPT/Excel

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
    private String score;        // 벌점

    @Column(nullable = true)
    private String score_date;        // 벌점 날짜

    @Column(nullable = true)
    private String accident;         // 사고

    @Column(nullable = true)
    private String reason;         // 벌점

    @Column(nullable = true)
    private String education;         // 교육 수료

    @Column(nullable = true)
    private String edu_cert;         // 수료증번호

    @Column(nullable = true)
    private String complete;         // 수료여부

    @Column(nullable = true)
    private String depart;         // 부서

    @Column(nullable = true)
    private String early;         // 조기인턴유무

    @Column(nullable = true)
    private String other;         // 타기업 취업

    @Column(nullable = true)
    private String smoke;         // 흡연

    @Column(nullable = true)
    private String disease;         // 질병

    @Column(nullable = true)
    private String opinion;         // 종합 의견

    @Column(nullable = true)
    private String content;         // 상담내용

    @Column(nullable = true)
    private String etc;             // 기타

}
