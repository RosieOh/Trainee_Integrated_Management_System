package com.lms.domain.member.controller;

import com.lms.domain.Course.dto.CourseDTO;
import com.lms.domain.Course.service.CourseService;
import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.service.MemberService;
import com.lms.global.cosntant.Role;
import com.lms.global.cosntant.Status;
import com.lms.global.cosntant.Subject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final CourseService courseService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("login")
    public String Login(Model model) {
        return "member/login";
    }

    @GetMapping("active")
    public String active(Model model, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "member/active";
    }

    @GetMapping("join")
    public String joinForm(Model model) {
        return "member/join";
    }

    @PostMapping("/idCheckPro")
    public ResponseEntity idCheck(@RequestBody MemberDTO memberDTO) throws Exception {
        String id = memberDTO.getId();
        boolean result = memberService.id_check(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/read")
    public String member_read(Model model, @RequestParam("id") String id){
        List<CourseDTO> course_big_List = courseService.course_subject_list(Subject.BIGDATA);
        List<CourseDTO> course_full_List = courseService.course_subject_list(Subject.FULLSTACK);
        List<CourseDTO> course_pm_List = courseService.course_subject_list(Subject.PM);
        model.addAttribute("course_big_List",course_big_List);
        model.addAttribute("course_full_List",course_full_List);
        model.addAttribute("course_pm_List",course_pm_List);
        log.info("member_start -----------");
        MemberDTO member = memberService.loginId(id);
        log.info("member -----------" + member);
        model.addAttribute("member", member);
        return "11";
    }


//
//    @PostMapping("joinPro")
//    public String join(Model model, MemberDTO memberDTO) {
//        memberService.memberInsert(memberDTO);
//        model.addAttribute("msg", "천재IT교육센터에 오신 것을 환영합니다!");
//        model.addAttribute("url", "/");
//        return "member/alert";
//    }
//


//    @GetMapping("remove")
//    public String remove(String email, Model model) {
//        MemberDTO memberDTO = memberService.getEmail(email);
//        memberDTO.setStatus(Status.OUT);
//        memberService.memberUpdate(memberDTO);
//        model.addAttribute("msg", "지금까지 감사합니다.");
//        model.addAttribute("url", "/logout");
//        return "/alert";
//    }
//
//    @PostMapping("changePw")
//    public String changePassword(Model model, String pw, String email) {
//        MemberDTO memberDTO = memberService.getEmail(email);
//        memberDTO.setPw(pw);
//        memberService.memberChangePw(memberDTO);
//        model.addAttribute("url", 2);
//        return "/alert";
//    }


}