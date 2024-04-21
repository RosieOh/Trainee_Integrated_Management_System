
package com.lms.domain;

import com.lms.domain.Course.dto.CourseDTO;
import com.lms.domain.Course.service.CourseService;
import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.service.MemberService;
import com.lms.global.cosntant.Role;
import com.lms.global.cosntant.Status;
import com.lms.global.cosntant.Subject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final MemberService memberService;
    private final CourseService courseService;


    @GetMapping("/")
    public String home() {
        memberService.createAdminMember(); // 관리자 회원 생성 메서드 호출
        return "/admin/member/list";
    }

    @GetMapping("join")
    public String join(Model model){
        List<CourseDTO> course_big_List = courseService.course_join_list(Subject.BIGDATA);
        List<CourseDTO> course_full_List = courseService.course_join_list(Subject.FULLSTACK);
        List<CourseDTO> course_pm_List = courseService.course_join_list(Subject.PM);
        model.addAttribute("course_big_List",course_big_List);
        model.addAttribute("course_full_List",course_full_List);
        model.addAttribute("course_pm_List",course_pm_List);
        return "user/member/join";
    }

    @PostMapping("/joinPro")
    public String joinPro(MemberDTO memberDTO, @RequestParam("cno") Integer cno){
        CourseDTO course = new CourseDTO();
        course.setNo(cno);
        memberDTO.setCourse(course);
        memberDTO.setStatus(Status.ACTIVE);
        memberDTO.setRole(Role.STUDENT);
        memberService.member_add(memberDTO);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "user/member/login";
    }

    @GetMapping("/status")
    public String status(Model model, Principal principal){
        String id = principal.getName();
        int pass = memberService.loginPro(id);
        if (pass == 1) {
            model.addAttribute("msg", "환영합니다! 로그인되었습니다");
            model.addAttribute("url", "/");
            return "/user/alert";
        } else if (pass == 2) {
            model.addAttribute("msg", "해당 계정은 휴면계정입니다. 휴면을 풀어주세요.");
            model.addAttribute("url", "/active");
            return "/user/alert";
        } else if (pass==3){
            model.addAttribute("msg", "해당 계정은 탈퇴한 계정입니다.");
            model.addAttribute("url", "/logout");
            return "/user/alert";
        } else if (pass==4){
            model.addAttribute("msg", "처음으로 오신걸 환영합니다 ^^");
            model.addAttribute("url", "/");
            return "/user/alert";
        }else {
            model.addAttribute("msg", "로그인 정보가 맞지 않습니다.");
            model.addAttribute("url", "/login");
            return "/user/alert";
        }
    }
}