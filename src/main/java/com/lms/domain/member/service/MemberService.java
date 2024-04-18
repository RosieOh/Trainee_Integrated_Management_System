package com.lms.domain.member.service;

import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.dto.MemberVO;
import com.lms.domain.member.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface MemberService {

    public List<MemberDTO> member_list();
    public PasswordEncoder passwordEncoder();
    public MemberDTO email_read(String email);
    public void member_add(MemberDTO memberDTO);
    public Member Login_email(String email);
    public void member_edit(MemberDTO memberDTO);
    public void state_edit(MemberDTO memberDTO);
    public void role_edit(MemberDTO memberDTO);
    public int login_pro(String email);
    public boolean id_check(String email);
    public void member_change_pw(MemberDTO memberDTO);
    public List<MemberDTO> memberVO_list(Integer cno);

    // 고정 아이디 생성
    void createAdminMember();
    boolean existByEmail(String email);







}