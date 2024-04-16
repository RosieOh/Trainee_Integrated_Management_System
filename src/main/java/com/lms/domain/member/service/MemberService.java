package com.lms.domain.member.service;

import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.dto.MemberVO;
import com.lms.domain.member.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface MemberService {

    public List<MemberDTO> memberList();
    public PasswordEncoder passwordEncoder();
    public MemberDTO getEmail(String email);
    public void memberInsert(MemberDTO memberDTO);
    public Member LoginEmail(String email);
    public void memberUpdate(MemberDTO memberDTO);
    public void stateUpdate(MemberDTO memberDTO);
    public void roleUpdate(MemberDTO memberDTO);
    public void memberDelete(Long id);
    public int loginPro(String email);
    public boolean idCheck(String email);
    public void memberChangePw(MemberDTO memberDTO);
    public List<MemberDTO> memberVOList(Integer cno);

    // 고정 아이디 생성
    void createAdminMember();
    boolean existByEmail(String email);







}