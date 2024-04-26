package com.lms.domain.member.service;

import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.List;

public interface MemberService {

    public List<MemberDTO> member_all_list();
    public List<MemberDTO> member_list();
    public List<MemberDTO> memberVO_list(Integer cno);
    public PasswordEncoder passwordEncoder();
    public MemberDTO member_read(Long no);
    public void member_add(MemberDTO memberDTO);
    public void member_edit(MemberDTO memberDTO);
    public MemberDTO loginId(String id);
    public boolean id_check(String id);
    public void member_change_pw(MemberDTO memberDTO);
    public void pw_reset(Long no);
    Member auth(String id);
    public int loginPro(String id);

    // 고정 아이디 생성
    void createAdminMember();

    //이름 가져오기
    public String getMemberName(Principal principal);
}