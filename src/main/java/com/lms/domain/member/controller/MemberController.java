package com.lms.domain.member.controller;

import com.lms.domain.Course.dto.CourseDTO;
import com.lms.domain.Course.service.CourseService;
import com.lms.domain.board.dto.BoardDTO;
import com.lms.domain.board.service.BoardService;
import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.service.MemberService;
import com.lms.domain.student.dto.StudentDTO;
import com.lms.domain.student.service.StudentService;
import com.lms.global.cosntant.Subject;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/member/")
public class MemberController {

    private final MemberService memberService;
    private final CourseService courseService;
    private final PasswordEncoder passwordEncoder;
    private final BoardService boardService;
    private final StudentService studentService;


    @GetMapping("/index")
    public String index(Principal principal, Model model) {
        String id = principal.getName();
        MemberDTO member = memberService.loginId(id);
        List<BoardDTO> noticeList = boardService.newNoticeList();
        model.addAttribute("member", member);
        model.addAttribute("noticeList", noticeList);
        return "user/index";
    }

    @GetMapping("/status")
    public String status(Model model, Principal principal){
        String id = principal.getName();
        int pass = memberService.loginPro(id);
        if (pass == 1) {
            model.addAttribute("msg", "환영합니다! 로그인되었습니다");
            model.addAttribute("url", "/member/index");
            return "/user/alert";
        } else if (pass == 2) {
            model.addAttribute("msg", "해당 계정은 휴면계정입니다. 관리자에게 문의해주세요.");
            model.addAttribute("url", "/logout");
            return "/user/alert";
        } else if (pass==3){
            model.addAttribute("msg", "해당 계정은 탈퇴한 계정입니다. 관리자에게 문의해주세요.");
            model.addAttribute("url", "/logout");
            return "/user/alert";
        } else if (pass==4){
            model.addAttribute("msg", "처음 오신걸 환영합니다 ^^");
            model.addAttribute("url", "/member/mypage2");
            return "/user/alert";
        }else {
            model.addAttribute("msg", "로그인 정보가 맞지 않습니다.");
            model.addAttribute("url", "/login");
            return "/user/alert";
        }
    }

    @GetMapping("mypage")
    public String mypage(Model model, Principal principal){
        String id = principal.getName();
        MemberDTO member = memberService.loginId(id);
        model.addAttribute("member", member);
        return "user/member/mypage";
    }
    @GetMapping("mypage2")
    public String mypage2(Model model, Principal principal){
        String id = principal.getName();
        MemberDTO memberDTO = memberService.loginId(id);
        StudentDTO studentDTO = studentService.student_read(memberDTO.getNo());
        model.addAttribute("studentDTO", studentDTO);
        return "user/member/mypage2";
    }

    @PostMapping("student_add")
    public String student_add(StudentDTO studentDTO){
        studentService.student_edit(studentDTO);
        log.info("studentDTO --------------" + studentDTO);
        return "redirect:/member/mypage2";
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