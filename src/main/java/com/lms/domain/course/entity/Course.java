package com.lms.domain.course.entity;

import com.lms.global.cosntant.BaseEntity;
import com.lms.global.cosntant.Subject;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;     // 기본키

    @Enumerated(EnumType.STRING)
    private Subject subject;        //과정

    @Column(nullable = false)
    private int flag;               //기수

    @Column(nullable = true)
    private String start_date;      //시작일

    @Column(nullable = true)
    private String end_date;        //종료일

    @Column(nullable = true)
    private String train_time;      // 80% 경과일자

    @Column(nullable = true)
    private String instructor;      //대표강사

    @Column(nullable = true)
    private String manager;        //담당 매니저

    @ColumnDefault("'n'")
    private String delete_type;        // 공개여부 y== 회원가입 공개, n == 비공개(종강), ing == 진행 중인 강의
}