package com.lms.domain.member.service;

import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.entity.Member;
import com.lms.global.util.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface MemberService {

    public List<MemberDTO> member_list();
    public PasswordEncoder passwordEncoder();
    public MemberDTO member_read(Long no);
    public void member_add(MemberDTO memberDTO);
    public void member_edit(MemberDTO memberDTO);
    public MemberDTO loginId(String id);
    public boolean id_check(String id);
    public void member_change_pw(MemberDTO memberDTO);
    public void pw_reset(Long no);
    public List<MemberDTO> memberVO_list(Integer cno);
    Member auth(String id);
    public int loginPro(String id);

    // 고정 아이디 생성
    void createAdminMember();

    Page <Member> search(String keyword, Pageable pageable);
    Page<Member> memberList(Pageable pageable);
}