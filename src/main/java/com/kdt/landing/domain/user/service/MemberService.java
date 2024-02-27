package com.kdt.landing.domain.user.service;

import com.kdt.landing.domain.user.dto.MemberJoinDTO;
import com.kdt.landing.domain.user.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface MemberService {
    void createAdminMember();

    static class MidExistException extends Exception {}
    Member existByEmail(String email);
    void join(MemberJoinDTO memberJoinDTO) ;

    void changePw(MemberJoinDTO memberJoinDTO);
    public PasswordEncoder passwordEncoder();



}