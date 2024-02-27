package com.kdt.landing.domain.user;

import com.kdt.landing.domain.user.dto.MemberJoinDTO;
import com.kdt.landing.domain.user.entity.Member;
import com.kdt.landing.domain.user.repository.MemberRepository;
import com.kdt.landing.domain.user.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Objects;

@Log4j2
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder passwordEncoder;


    @GetMapping("/member/login")
    public String login(Model model, Principal principal) {
        model.addAttribute("memberJoinDTO", new MemberJoinDTO());
        return "member/login";
    }

    @GetMapping("/member/loginFail")
    public String loginFail(Model model) {
        model.addAttribute("msg", "로그인 실패! 다시 시도해 주세요!");
        model.addAttribute("url", "login");
        return "layout/loginFail";
    }

    @GetMapping("/member/join")
    public String join(Model model) {
        model.addAttribute("memberJoinDTO", new MemberJoinDTO());
        return "member/join";
    }

    @PostMapping("/member/join")
    public String joinPOST(@Valid MemberJoinDTO memberJoinDTO, BindingResult bindingResult, Model model) {
        log.info("join post...");
        log.info(memberJoinDTO);
        String email = memberJoinDTO.getEmail();
        Member existEmail = memberService.existByEmail(email);
        System.out.println(existEmail);
        if (existEmail != null) {
            bindingResult
                    .rejectValue("email", "error.email", "사용이 불가한 이메일입니다.");
        }

        if (!Objects.equals(memberJoinDTO.getPasswordConfirm(), memberJoinDTO.getPw())) {
            bindingResult.rejectValue("passwordConfirm", "error.passwordConfirm", "비밀번호와 비밀번호 확인이 다릅니다.");
        }

        if (bindingResult.hasErrors()) {
            System.out.println("error" + bindingResult.hasErrors());
            System.out.println("e" + bindingResult.getFieldError().getDefaultMessage());
            model.addAttribute("error", bindingResult.hasErrors());
            model.addAttribute("memberJoinDTO", memberJoinDTO);
            return "member/login";
        }

        memberService.join(memberJoinDTO);
        return "redirect:login";
    }
}