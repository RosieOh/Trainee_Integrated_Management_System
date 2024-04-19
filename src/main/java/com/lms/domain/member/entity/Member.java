package com.lms.domain.member.entity;

import com.lms.domain.Course.entity.Course;
import com.lms.domain.member.dto.MemberDTO;
import com.lms.global.cosntant.BaseEntity;
import com.lms.global.cosntant.Role;
import com.lms.global.cosntant.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "roleSet")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;     // 기본키

    @Column(unique = true, nullable = true)
    private String login_id;           // 유저가 사용하는 아이디

    @Column(nullable = true)
    private String pw;              //비밀번호

    @Column(nullable = true)
    private String email;              // 이메일

    @Column(nullable = true)
    private String name;            //유저 이름

    @Column(nullable = true)
    private String gender;          //성별

    @Column(nullable = true)
    private String birth;           //생년월일

    @Column(nullable = true)
    private String phone;           //연락처

    @Column(nullable = true)
    private String addr1;         // 기본 주소

    @Column(nullable = true)
    private String addr2;         // 상세 주소

    @Column(nullable = true)
    private String postcode;         //우편번호

    @Enumerated(EnumType.STRING)
    private Status status;          //회원 활동상태

    @CreatedDate
    private LocalDateTime loginAt;  //최종 로그인시간

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.STUDENT; // 디폴트로 USER 권한을 갖도록 초기화

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "cno", referencedColumnName = "no")
    private Course course;      // 강의 분류

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Role> roleSet = new HashSet<>();


    public void change(MemberDTO memberDTO) {
        this.phone = memberDTO.getPhone();
    }

    public void stateUpdate(MemberDTO memberDTO) {
        this.status = memberDTO.getStatus();
    }

    public void roleUpdate(MemberDTO memberDTO) {
        this.role = memberDTO.getRole();
    }

    @Builder
    public Member(String email, String pw, String name) {
        this.name = name;
        this.pw = pw;
        this.email = email;
    }

}