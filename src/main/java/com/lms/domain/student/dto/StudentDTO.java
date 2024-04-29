package com.lms.domain.student.dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@ToString
public class StudentDTO{

    private Long no;


    //    학생들 입력
    
    private String stack;           //기술스택

    
    private String cert;            //자격증


    private String univ;            //대학교

    
    private String major;            //학과

    
    private String job_program;      //국취참여여부

    
    private String fund;            //지원센터


    private String hope;            //희망 진로분야


    private Long picture;            //사진


    private Long resume;            //이력서


    private Long portfolio;           //포트폴리오


    //    매니저 입력


    
    private String exp;             //경력

    
    private String OA;              // Word/PPT/Excel

    
    private String satis;           // 전반적 만족도

    
    private String edu_data;        // 학습자료

    
    private String instructor;      // 담당 강사

    
    private String task;            // 과제 및 피드백

    
    private String env;             // 학습환경 및 장비

    
    private String goal;            // 최종목표

    
    private String doc;             // 입사지원 서류관련 의견

    
    private String personal;        // 성격 및 태도

    
    private String score;        // 벌점

    
    private String score_date;        // 벌점 날짜

    
    private String accident;         // 사고

    
    private String reason;         // 사유

    
    private String education;         // 교육 수료

    
    private String edu_cert;         // 수료증번호

    
    private String complete;         // 수료여부

    
    private String depart;         // 부서

    
    private String early;         // 조기인턴유무

    
    private String other;         // 타기업 취업

    
    private String smoke;         // 흡연

    
    private String disease;         // 질병

    
    private String opinion;         // 종합 의견

    
    private String content;         // 상담내용

    
    private String etc;             // 기타

}
