package com.lms.domain.Course.dto;

import com.lms.global.cosntant.Subject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CourseDTO{

    private Integer no;     // 기본키

    @Enumerated(EnumType.STRING)
    private Subject subject;        //과정

    private int flag;               //기수

    private String start_date;      //시작일

    private String end_date;        //종료일
    
    private String train_time;        //훈련시간
    
    private String instructor;        //대표강사
    
    private String manager;          //담당 매니저

    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;
}