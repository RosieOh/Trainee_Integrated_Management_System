package com.lms.domain.member.dto;

import com.lms.domain.Course.dto.CourseDTO;
import com.lms.domain.Course.entity.Course;
import com.lms.global.cosntant.Role;
import com.lms.global.cosntant.Status;
import com.lms.global.cosntant.Subject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
public class MemberDTO {

    private Long no;     // 기본키

    private String oauth2Id;

    private String provider;

    private String providerId;

    @NotBlank(message = "**")

    private String email;           // 유저가 사용하는 이메일

    @NotBlank(message = "**")
    private String pw;              //비밀번호

    @NotBlank(message = "**")
    private String name;            //유저 이름

    @NotBlank(message = "**")
    private String gender;          //성별

    @NotBlank(message = "**")
    private String birth;           //생년월일

    @NotBlank(message = "**")
    private String phone;           //연락처

    @NotBlank(message = "**")
    private String address;         //주소

    @NotBlank(message = "**")
    private String univ;            //대학교

    @NotBlank(message = "**")
    private String major;           //학과

    @NotBlank(message = "**")
    private String job_program;     //국취참여여부

    @NotBlank(message = "**")
    private String fund;            //지원센터

    @NotBlank(message = "**")
    private String cert;            //수료증번호

    @NotBlank(message = "**")
    private String picture;         //프로필 사진

    @NotBlank(message = "**")
    private String complete;        //수료여부

    @Enumerated(EnumType.STRING)
    private Subject subject;        //과정

    @Enumerated(EnumType.STRING)
    private Status status;          //회원 활동상태

    @CreatedDate
    private LocalDateTime loginAt;  //최종 로그인시간

    @Enumerated(EnumType.STRING)
    private Role role;              //권한

    @NotNull
    private CourseDTO courseDTO;      // 강좌 번호

}