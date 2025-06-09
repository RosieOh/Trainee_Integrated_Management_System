package com.lms.domain.member.dto;

import com.lms.domain.course.dto.CourseDTO;
import com.lms.global.cosntant.Role;
import com.lms.global.cosntant.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
public class MemberDTO {
    private Long no;             // 기본키
    private String id;           // 유저가 사용하는 아이디
    private String pw;           // 비밀번호
    private String email;        // 이메일
    private String name;         // 유저 이름
    private String gender;       // 성별
    private String birth;        // 생년월일
    private String phone;        // 연락처
    private String addr1;        // 기본 주소
    private String addr2;        // 상세 주소
    private String postcode;     //우편번호

    @Enumerated(EnumType.STRING)
    private Status status;          //회원 활동상태
    private LocalDateTime loginAt;  //최종 로그인시간

    @Enumerated(EnumType.STRING)
    private Role role;             // 디폴트로 USER 권한을 갖도록 초기화
    private CourseDTO course;      // 강좌 번호
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

}