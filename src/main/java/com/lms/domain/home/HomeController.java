
package com.lms.domain.home;

import com.lms.domain.Course.dto.CourseDTO;
import com.lms.domain.Course.service.CourseService;
import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.dto.MemberVO;
import com.lms.domain.member.repository.MemberRepository;
import com.lms.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        CourseDTO courseDTO = courseService.course_One(1);
        List<MemberDTO> memberList = memberService.memberList();
        List<CourseDTO> courseList = courseService.course_List();
        model.addAttribute("memberList",memberList);
        model.addAttribute("courseList",courseList);
        log.info("memberListㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ" + memberList);
        log.info("courseListㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ" + courseList);
        return "index";
    }

}