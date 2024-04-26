package com.lms.domain.member.service;

import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.entity.Member;
import com.lms.global.cosntant.Role;
import com.lms.global.cosntant.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    //검색 및 페이징처리
    Page<Member> findByKeywordAndFlagAndSubjectAndRole(String keyword, Integer flag, Subject subject, Role role, Pageable pageable);
    Page<Member> memberList(Pageable pageable);

    //이름 가져오기
    public String getMemberName(Principal principal);
}