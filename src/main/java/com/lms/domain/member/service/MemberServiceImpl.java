package com.lms.domain.member.service;

import com.lms.domain.Course.dto.CourseDTO;
import com.lms.domain.Course.entity.Course;
import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.dto.MemberVO;
import com.lms.domain.member.entity.Member;
import com.lms.domain.member.repository.MemberRepository;
import com.lms.global.cosntant.Role;
import com.lms.global.cosntant.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void createAdminMember() {
        // 이미 존재하는 회원인지 확인
        if (!memberRepository.existsByEmail("admin@chunjaeIT.co.kr")) {
            // 관리자 계정 생성 및 초기 설정
            Member admin = Member.builder()
                    .pw(passwordEncoder.encode("1234"))
                    .name("관리자")
                    .email("admin@chunjaeIT.co.kr")
                    .role(Role.ADMIN)
                    .status(Status.ACTIVE)
                    .phone("02-3282-8589")
                    .build();

            memberRepository.save(admin);

            log.info("Admin 계정이 생성되었습니다.");
        } else {
            log.info("Admin 계정이 이미 존재합니다.");
        }
    }

    @Override
    public boolean existByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }

    @Override
    public List<MemberDTO> member_list() {
        List<Member> memberList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = memberList.stream().map(
                member -> modelMapper.map(member,MemberDTO.class))
                .collect(Collectors.toList());
        return memberDTOList;
    }

    @Override
    public void member_add(MemberDTO memberDTO) {
        String password = passwordEncoder.encode(memberDTO.getPw());
        memberDTO.setPw(password);
        memberDTO.setRole(Role.STUDENT);
        memberDTO.setStatus(Status.ACTIVE);
        Member member = modelMapper.map(memberDTO, Member.class);
        memberRepository.save(member);
    }

    @Override
    public void member_edit(MemberDTO memberDTO) {
        Optional<Member> member = memberRepository.getMember(memberDTO.getEmail());
        Member member1 = member.orElseThrow();
        member1.change(memberDTO);
        memberRepository.save(member1);
    }

    @Override
    public void state_edit(MemberDTO memberDTO) {
        Optional<Member> member = memberRepository.getMember(memberDTO.getEmail());
        Member member1 = member.orElseThrow();
        member1.stateUpdate(memberDTO);
        memberRepository.save(member1);
    }

    @Override
    public void role_edit(MemberDTO memberDTO) {
        Optional<Member> member = memberRepository.getMember(memberDTO.getEmail());
        Member member1 = member.orElseThrow();
        member1.roleUpdate(memberDTO);
        memberRepository.save(member1);
    }

    @Override
    public MemberDTO login_id(String id) {
        Optional<Member> member = memberRepository.id_read("id");
        MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);
        return memberDTO;
    }

    @Override
    public int login_pro(String id) {
        int pass = 0;
        Member member = memberRepository.id_read2(id);
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(30); // 현재 시점에서 30일 동안 반응이 없으면 휴면
        if (localDateTime.isAfter(member.getLoginAt())) {
            member.setStatus(Status.REST);
            memberRepository.save(member);
            pass = 2;
        } else {
            if (member.getStatus().equals(Status.ACTIVE)) {
                member.setLoginAt(LocalDateTime.now());
                memberRepository.save(member);
                pass = 2;
            } else if (member.getStatus().equals(Status.REST)) {
                pass = 2;
            } else if (member.getStatus().equals(Status.OUT)) {
                pass = 3;
            }
        }
        return pass;
    }

    @Override
    public boolean id_check(String id) {
        boolean pass = true;
        int cnt = memberRepository.countByEmail(id);
        if(cnt > 0) pass = false;
        return pass;
    }

    @Override
    public void member_change_pw(MemberDTO memberDTO) {
        String password = passwordEncoder.encode(memberDTO.getPw());
        memberDTO.setPw(password);
        Member member = modelMapper.map(memberDTO, Member.class);
        memberRepository.save(member);
    }


    @Override
    public List<MemberDTO> memberVO_list(Integer cno) {
        List<Member> memberList = memberRepository.voList2(cno);
        List<MemberDTO> memberDTOList = memberList.stream().map(
                        member -> modelMapper.map(member,MemberDTO.class))
                .collect(Collectors.toList());
        return memberDTOList;
    }

}
