
package com.lms.domain.home;

import com.lms.domain.Course.dto.CourseDTO;
import com.lms.domain.Course.entity.Course;
import com.lms.domain.Course.service.CourseService;
import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.dto.MemberVO;
import com.lms.domain.member.entity.Member;
import com.lms.domain.member.repository.MemberRepository;
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

import java.net.http.HttpRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final MemberService memberService;
    private final CourseService courseService;
    private final MemberRepository memberRepository;



    @GetMapping("/")
    public String home(Principal principal,Model model) {
        Integer a = 2;
        CourseDTO courseDTO = courseService.course_One(14);
        List<MemberDTO> memberList = memberService.memberList();
        List<CourseDTO> courseList = courseService.course_List();
        List<MemberDTO> memberVOList = memberService.memberVOList2(14);
        List<Member> memberVOList1 = memberService.memberVOList1();
        model.addAttribute("memberList",memberList);
        model.addAttribute("courseList",courseList);
        model.addAttribute("memberVOList",memberVOList);
        model.addAttribute("memberVOList1",memberVOList1);
        log.info("memberList ㅡㅡㅡㅡㅡㅡㅡㅡㅡ" + memberList);
        log.info("memberVOList ㅡㅡㅡㅡㅡㅡㅡㅡㅡ" + memberVOList);
        log.info("courseList ㅡㅡㅡㅡㅡㅡㅡㅡㅡ" + courseList);
        log.info("memberVOList1 ㅡㅡㅡㅡㅡㅡㅡㅡㅡ" + memberVOList1);

        return "index";
    }

    @GetMapping("join")
    public String join(Model model){
        List<CourseDTO> courseDTOList = courseService.course_List();
        model.addAttribute("courseDTOList", courseDTOList);
        return "join";
    }

    @PostMapping("/joinPro")
    public String joinPro(Model model, MemberDTO memberDTO, @RequestParam("cno") Integer cno){
        log.info("memberDTO ㅡㅡㅡㅡㅡㅡㅡㅡㅡ" + memberDTO);

        CourseDTO course = new CourseDTO();
        course.setNo(cno);
        log.info("courseDTO ㅡㅡㅡㅡㅡㅡㅡㅡㅡ" + course);
        memberDTO.setCourse(course);
        memberDTO.setStatus(Status.ACTIVE);
        memberDTO.setRole(Role.STUDENT);
        log.info("memberDTOInsert ㅡㅡㅡㅡㅡㅡㅡㅡㅡ" + memberDTO);

        memberService.memberInsert(memberDTO);
        return "redirect:/";
    }

    @GetMapping("/course")
    public String course(){
        return "course";
    }

    @PostMapping("/coursePro")
    public String coursePro(CourseDTO courseDTO){
        log.info("courseDTO ㅡㅡㅡㅡㅡㅡㅡㅡ" + courseDTO);
        courseService.course_Add(courseDTO);

        return "redirect:/";
    }
    @GetMapping("/memJoin")
    public String memJoin(){
        return "member/join";
    }

    @GetMapping("/login")
    public String login(){
        return "member/login";
    }

}