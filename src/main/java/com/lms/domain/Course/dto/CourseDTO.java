package com.lms.domain.Course.dto;

import com.lms.global.cosntant.Subject;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
    
    private String train_time;        //80% 경과일자
    
    private String instructor;        //대표강사
    
    private String manager;          //담당 매니저

    private String delete_type;        // 공개여부

    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;
}