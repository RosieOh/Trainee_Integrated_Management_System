package com.lms.domain.member.service;

import com.lms.domain.board.entity.Board;
import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.entity.Member;
import com.lms.global.cosntant.Role;
import com.lms.global.cosntant.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
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
    public boolean validatePw(String id, String pw);
    public void changePw(String id, String pw);

    // 고정 아이디 생성
    void createAdminMember();

    //이름 가져오기
    public String getMemberName(Principal principal);

    //검색 및 페이징 처리
    public Page<Member> adminSearch(String keyword, Integer flag, Subject subject, Role role, Pageable pageable);
    public Page<Member> managerSearch(String keyword, Integer flag, Subject subject, Role role, Pageable pageable);

    //공지사항의 작성자 이름 가져오기 == id로 이름 찾기
    public String getNameById(String id);
}