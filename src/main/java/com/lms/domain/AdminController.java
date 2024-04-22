
package com.lms.domain;

import com.lms.domain.Course.dto.CourseDTO;
import com.lms.domain.Course.service.CourseService;
import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.entity.Member;
import com.lms.domain.member.service.MemberService;
import com.lms.global.cosntant.Role;
import com.lms.global.cosntant.Status;
import com.lms.global.cosntant.Subject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final MemberService memberService;
    private final CourseService courseService;

    @GetMapping("/member")
    public String board(Model model, Principal principal, Integer cno){
        List<CourseDTO> course_big_List = courseService.course_subject_list(Subject.BIGDATA);
        List<CourseDTO> course_full_List = courseService.course_subject_list(Subject.FULLSTACK);
        List<CourseDTO> course_pm_List = courseService.course_subject_list(Subject.PM);
        List<MemberDTO> memberList = memberService.member_list();
        List<MemberDTO> memberVOList = memberService.memberVO_list(cno);
        model.addAttribute("memberList",memberList);
        model.addAttribute("memberVOList",memberVOList);
        model.addAttribute("course_big_List",course_big_List);
        model.addAttribute("course_full_List",course_full_List);
        model.addAttribute("course_pm_List",course_pm_List);
        model.addAttribute("cno",cno);
        return "admin/member/list";
    }

    @GetMapping("/course")
    public String course(Model model){
        List<CourseDTO> courseDTOList = courseService.course_list();
        model.addAttribute("courseDTOList",courseDTOList);
        return "admin/course";
    }

    @PostMapping("/coursePro")
    public String coursePro(CourseDTO courseDTO){
        courseService.course_add(courseDTO);
        return "redirect:/admin/course";
    }

    @PostMapping("/course_delete")
    public String course_delete(CourseDTO courseDTO) {
        courseService.delete_type(courseDTO);
        return "redirect:/admin/course";
    }

    @GetMapping("/member_read")
    public String member_read(Model model,String id){
//        List<CourseDTO> course_big_List = courseService.course_subject_list(Subject.BIGDATA);
//        List<CourseDTO> course_full_List = courseService.course_subject_list(Subject.FULLSTACK);
//        List<CourseDTO> course_pm_List = courseService.course_subject_list(Subject.PM);
//        model.addAttribute("course_big_List",course_big_List);
//        model.addAttribute("course_full_List",course_full_List);
//        model.addAttribute("course_pm_List",course_pm_List);
        log.info("member_start -----------");
        Member member = memberService.auth(id);
        log.info("member -----------" + member);
        model.addAttribute("member", member);
        return "/admin/member/read";
    }

    @PostMapping("/change_status")
    public String out1(String id, Model model, Status status){
        MemberDTO memberDTO = memberService.loginId(id);
        memberDTO.setStatus(status);
        memberService.state_edit(memberDTO);
        return "redirect:/";
    }

    @PostMapping("/change_role")
    public String role1(String id, Model model, Role role){
        MemberDTO memberDTO = memberService.loginId(id);
        memberDTO.setRole(role);
        memberService.member_edit(memberDTO);
        return "redirect:/";
    }

}