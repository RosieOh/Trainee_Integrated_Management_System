
package com.lms.domain.home;

import com.lms.domain.Course.dto.CourseDTO;
import com.lms.domain.Course.service.CourseService;
import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.repository.MemberRepository;
import com.lms.domain.member.service.MemberService;
import com.lms.global.cosntant.Role;
import com.lms.global.cosntant.Status;
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
    public String home(Principal principal,Model model) {
        memberService.createAdminMember(); // 관리자 회원 생성 메서드 호출
        return "redirect:/member";
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
    public String course(Model model){
        List<CourseDTO> courseDTOList = courseService.course_List();
        model.addAttribute("courseDTOList",courseDTOList);
        return "course";
    }

    @PostMapping("/coursePro")
    public String coursePro(CourseDTO courseDTO){
        courseService.course_Add(courseDTO);
        return "redirect:/course";
    }
    @GetMapping("/memJoin")
    public String memJoin(){
        return "member/join";
    }

    @GetMapping("/login")
    public String login(){
        return "member/login";
    }

    @GetMapping("/member")
    public String board(Model model, Principal principal, Integer cno){
        List<MemberDTO> memberDTOList = memberService.memberList();
        List<MemberDTO> memberVOList = memberService.memberVOList(cno);
        model.addAttribute("memberDTOList",memberDTOList);
        model.addAttribute("memberVOList",memberVOList);
        return "member";
    }

    @GetMapping("/board")
    public String board(){
        return "board";
    }
}