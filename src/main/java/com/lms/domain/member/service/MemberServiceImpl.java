package com.lms.domain.member.service;

import com.lms.domain.Course.entity.Course;
import com.lms.domain.Course.repository.CourseRepository;
import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.entity.Member;
import com.lms.domain.member.repository.MemberRepository;
import com.lms.domain.student.entity.Student;
import com.lms.domain.student.repository.StudentRepository;
import com.lms.global.cosntant.Role;
import com.lms.global.cosntant.Status;
import com.lms.global.cosntant.Subject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createAdminMember() {
        // 이미 존재하는 회원인지 확인
        if (memberRepository.countId("admin") <= 0) {
            Course course = Course.builder()
                    .no(1)
                    .subject(Subject.EXCEPTION)
                    .flag(0)
                    .build();
            courseRepository.save(course);
            // 관리자 계정 생성 및 초기 설정
            Member admin = Member.builder()
                    .id("admin")
                    .pw(passwordEncoder.encode("1234"))
                    .name("관리자")
                    .email("admin@chunjaeIT.co.kr")
                    .role(Role.ADMIN)
                    .status(Status.ACTIVE)
                    .phone("02-3282-8589")
                    .course(course)
                    .build();
            memberRepository.save(admin);
            Member member = memberRepository.findId(admin.getId());
            Student student = Student.builder()
                    .no(member.getNo())
                    .build();
            studentRepository.save(student);
            log.info("Admin 계정이 생성되었습니다.");
        } else {
            log.info("Admin 계정이 이미 존재합니다.");
        }
    }

    @Override
    public List<MemberDTO> member_all_list() {
        List<Member> memberList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = memberList.stream().map(
                        member -> modelMapper.map(member, MemberDTO.class))
                .collect(Collectors.toList());
        return memberDTOList;
    }

    @Override
    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }

    @Override
    public List<MemberDTO> member_list() {
        List<Member> memberList = memberRepository.member_list();
        List<MemberDTO> memberDTOList = memberList.stream().map(
                        member -> modelMapper.map(member, MemberDTO.class))
                .collect(Collectors.toList());
        return memberDTOList;
    }

    @Override
    public List<MemberDTO> memberVO_list(Integer cno) {
        List<Member> memberList = memberRepository.voList2(cno);
        List<MemberDTO> memberDTOList = memberList.stream().map(
                        member -> modelMapper.map(member, MemberDTO.class))
                .collect(Collectors.toList());
        return memberDTOList;
    }

    @Override
    public MemberDTO member_read(Long no) {
        Optional<Member> member = memberRepository.findById(no);
        MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);
        return memberDTO;
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
    @Transactional
    public void member_edit(MemberDTO memberDTO) {
        Member member = modelMapper.map(memberDTO, Member.class);
        memberRepository.save(member);
    }

    @Override
    public MemberDTO loginId(String id) {
        Optional<Member> mem = memberRepository.id_read(id);
        MemberDTO memberDTO = modelMapper.map(mem, MemberDTO.class);
        return memberDTO;
    }

    @Override
    public boolean id_check(String id) {
        boolean pass = true;
        int cnt = memberRepository.countId(id);
        if (cnt > 0) pass = false;
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
    public void pw_reset(Long no) {
        Optional<Member> member = memberRepository.findById(no);
        MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);
        String password = passwordEncoder.encode("1234");
        memberDTO.setPw(password);
        Member member1 = modelMapper.map(memberDTO, Member.class);
        memberRepository.save(member1);
    }


    @Override
    public Member auth(String id) {
        Member member = memberRepository.findId(id);
        return member;
    }

    @Override
    public int loginPro(String id) {
        int pass = 0;
        Member member = memberRepository.findId(id);
        LocalDateTime local = LocalDateTime.now().minusDays(30);
        if (member.getLoginAt() == null) {
            member.setLoginAt(LocalDateTime.now());
            memberRepository.save(member);
            pass = 4;
        } else {
            if (local.isAfter(member.getLoginAt())) {
                member.setStatus(Status.REST);
                memberRepository.save(member);
                pass = 2;
            } else {
                if (member.getStatus().equals(Status.ACTIVE)) {
                    member.setLoginAt(LocalDateTime.now());
                    memberRepository.save(member);
                    pass = 1;
                } else if (member.getStatus().equals(Status.REST)) {
                    pass = 2;
                } else if (member.getStatus().equals(Status.OUT)) {
                    pass = 3;
                }
            }
        }
        return pass;
    }

    @Override
    public Page<Member> findByKeywordAndFlagAndSubjectAndRole(String keyword, Integer flag, Subject subject, Role role, Pageable pageable) {
        return memberRepository.findByKeywordAndFlagAndSubjectAndRole(keyword, flag, subject, role, pageable);
    }

    @Override
    public Page<Member> memberList(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    @Override
    public String getMemberName(Principal principal) {
        String name = memberRepository.findId(principal.getName()).getName();
        return name;
    }
}
