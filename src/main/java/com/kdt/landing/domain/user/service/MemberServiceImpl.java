package com.kdt.landing.domain.user.service;

import com.kdt.landing.domain.user.dto.MemberJoinDTO;
import com.kdt.landing.domain.user.entity.Member;
import com.kdt.landing.domain.user.repository.MemberRepository;
import com.kdt.landing.global.cosntant.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public void join(MemberJoinDTO memberJoinDTO) {

        Member member = modelMapper.map(memberJoinDTO, Member.class);
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getPw()));
        member.addRole(Role.STUDENT);
        log.info("=======================");
        log.info(member);
        log.info(member.getRoleSet());
        memberRepository.save(member);
    }

    @Override
    public void createAdminMember() {
        // 이미 존재하는 회원인지 확인
        if (!memberRepository.existsByEmail("admin@naver.com")) {
            // 관리자 계정 생성 및 초기 설정
            Member admin = Member.builder()
                    .pw(passwordEncoder.encode("1234"))
                    .name("Admin")
                    .nickname("관리자")
                    .email("admin@naver.com")
                    .active(1)
                    .roleSet(Collections.singleton(Role.ADMIN))
                    .role(Role.valueOf("ADMIN"))
                    .build();

            memberRepository.save(admin);

            log.info("Admin 계정이 생성되었습니다.");
        } else {
            log.info("Admin 계정이 이미 존재합니다.");
        }
    }

    @Override
    public Member existByEmail(String email) {
        return memberRepository.existsMemberByEmail(email);
    }

    @Override
    public void changePw(MemberJoinDTO memberJoinDTO) {

        Optional<Member> result = memberRepository.findById(Long.valueOf(memberJoinDTO.getId()));
        Member member = result.orElseThrow();
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getPw()));
        memberRepository.save(member);
    }

    @Override
    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }
}
