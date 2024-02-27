package com.kdt.landing.domain.user.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
public class MemberJoinDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "**")
    private String pw;

    @NotBlank(message = "**")
    private String name;

    @NotBlank(message = "**")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "**")
    private String code;

    @NotBlank(message = "**")
    private String nickname;

    @NotBlank(message = "**")
    private String school;

    @ColumnDefault("0")
    private int active;

    private String nowPassword;
    private String passwordConfirm;
}