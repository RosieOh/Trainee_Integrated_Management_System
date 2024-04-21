package com.lms.domain.member.service;

import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.dto.MemberVO;
import com.lms.domain.member.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface MemberService {

    public List<MemberDTO> member_list();
    public PasswordEncoder passwordEncoder();
    public void member_add(MemberDTO memberDTO);
    public void member_edit(MemberDTO memberDTO);
    public void state_edit(MemberDTO memberDTO);
    public void role_edit(MemberDTO memberDTO);
    public MemberDTO loginId(String id);
    public int login_pro(String id);
    public boolean id_check(String id);
    public void member_change_pw(MemberDTO memberDTO);
    public List<MemberDTO> memberVO_list(Integer cno);
    Member auth(String id);
    public int loginPro(String id);


    // 고정 아이디 생성
    void createAdminMember();

}