package com.lms.domain.Course.dto;

import com.lms.global.cosntant.Subject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@ToString
public class CourseDTO{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;     // 기본키

    @Enumerated(EnumType.STRING)
    private Subject subject;        //과정

    @NotBlank(message = "**")
    private int flag;               //기수

    @NotBlank(message = "**")
    private String start_date;      //시작일

    @NotBlank(message = "**")
    private String end_date;        //종료일
    
    @NotBlank(message = "**")
    private String train_time;        //훈련시간
    
    @NotBlank(message = "**")
    private String instructor;        //대표강사
    
    @NotBlank(message = "**")
    private String manager;          //담당 매니저
}