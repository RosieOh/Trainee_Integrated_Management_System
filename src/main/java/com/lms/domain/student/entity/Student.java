package com.lms.domain.student.entity;

import com.lms.global.cosntant.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(unique = true, nullable = false)
    private String email;           // 유저가 사용하는 이메일

    @Column(nullable = true)
    private String smoke;            //흡연

    @Column(nullable = true)
    private String disease;            //질병

    @Column(nullable = true)
    private String accident;            //사고

    @Column(nullable = true)
    private String etc;            //기타

    @Column(nullable = true)
    private String reason;            //사유

    @Column(nullable = true)
    private String score;            //벌점

    @Column(nullable = true)
    private String date;            //날짜

    @Column(nullable = true)
    private String education;            //교육수료

    @Column(nullable = true)
    private String certi;            //자격증

    @Column(nullable = true)
    private String workshop;            //워크샵

    @Column(nullable = true)
    private String cv;            //자기소개서

    @Column(nullable = true)
    private String resume;            //이력서

    @Column(nullable = true)
    private String univ;            //대학교

    @Column(nullable = true)
    private String major;           //학과

    @Column(nullable = true)
    private String job_program;     //국취참여여부

    @Column(nullable = true)
    private String fund;            //지원센터

    @Column(nullable = true)
    private String cert;            //수료증번호

    @Column(nullable = true)
    private String picture;         //프로필 사진

    @Column(nullable = true)
    private String complete;        //수료여부

    @Column(nullable = true)
    private String depart;        //부서

    @Column(nullable = true)
    private String hope;        //희망부서

    @Column(nullable = true)
    private String early;        //조기인턴유무

    @Column(nullable = true)
    private String other;        //타기업취업

    @Column(nullable = true)
    private String portfolio;        //포트폴리오

}
