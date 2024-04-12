
package com.lms.domain.home;

import com.lms.domain.Course.dto.CourseDTO;
import com.lms.domain.Course.service.CourseService;
import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.dto.MemberVO;
import com.lms.domain.member.entity.Member;
import com.lms.domain.member.repository.MemberRepository;
import com.lms.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final CourseService courseService;
    private final MemberRepository memberRepository;



    @GetMapping("/")
    public String home(Principal principal,Model model) {
        Integer a = 2;
        CourseDTO courseDTO = courseService.course_One(1);
        List<MemberDTO> memberList = memberService.memberList();
        List<CourseDTO> courseList = courseService.course_List();
        List<Member> memberVOList = memberService.memberVOList2(a);
        model.addAttribute("memberList",memberList);
        model.addAttribute("courseList",courseList);
        model.addAttribute("memberVOList",memberVOList);
        log.info("memberList ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ" + memberList);
        log.info("courseList ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ" + courseList);
        log.info("memberVOList ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ" + memberVOList);
        return "index";
    }

    @GetMapping("/join")
    public String join(Model model){
        List<CourseDTO> courseDTOList = courseService.course_List();
        model.addAttribute("courseDTOList", courseDTOList);
        return "join";
    }

    @PostMapping("/join")
    public String joinpro(){

        return "redirect:/";
    }
    @GetMapping("/course")
    public String course(){
        return "join";
    }

}