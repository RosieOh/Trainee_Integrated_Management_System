
package com.lms.domain;

import com.lms.domain.Course.dto.CourseDTO;
import com.lms.domain.Course.service.CourseService;
import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.entity.Member;
import com.lms.domain.member.service.MemberService;
import com.lms.domain.student.dto.StudentDTO;
import com.lms.domain.student.service.StudentService;
import com.lms.global.cosntant.Role;
import com.lms.global.cosntant.Status;
import com.lms.global.cosntant.Subject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final MemberService memberService;
    private final CourseService courseService;
    private final StudentService studentService;

    @GetMapping("/member")
    public String board(Model model, Principal principal, Integer cno, HttpServletRequest request){
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


    @GetMapping("/memList")
    public String memberList(HttpServletRequest request, Model model, Integer cno, @PageableDefault(page=0, size=5, sort="name", direction= Sort.Direction.ASC)Pageable pageable,
                             @RequestParam(required = false) String keyword, @RequestParam(required = false)Subject subject, @RequestParam(required = false)Integer flag, @RequestParam(required = false)Role role) {
        List<CourseDTO> course_big_List = courseService.course_subject_list(Subject.BIGDATA);
        List<CourseDTO> course_full_List = courseService.course_subject_list(Subject.FULLSTACK);
        List<CourseDTO> course_pm_List = courseService.course_subject_list(Subject.PM);
        List<MemberDTO> memberVOList = memberService.memberVO_list(cno);
        Subject[] subjects = {Subject.BIGDATA, Subject.FULLSTACK, Subject.PM};
        Role[] roles = {Role.STUDENT, Role.TEACHER};
        model.addAttribute("subjects", subjects);
        model.addAttribute("roles", roles);
        model.addAttribute("subjects", subjects);
        model.addAttribute("memberVOList",memberVOList);
        model.addAttribute("course_big_List",course_big_List);
        model.addAttribute("course_full_List",course_full_List);
        model.addAttribute("course_pm_List",course_pm_List);
        model.addAttribute("cno",cno);

        Page<Member> list;

        if(keyword != null ) {
            if( flag != null && subject != null ) {
                if (role != null) {
                    list = memberService.findByKeywordAndFlagAndSubjectAndRole(keyword, flag, subject, role, pageable);
                } else {
                    list = memberService.findByKeywordAndFlagAndSubjectAndRole(keyword, flag, subject, null, pageable);
                }
            } else {
                if (role != null) {
                    list = memberService.findByKeywordAndFlagAndSubjectAndRole(keyword, null, null, role, pageable);
                } else {
                    list = memberService.findByKeywordAndFlagAndSubjectAndRole(keyword, null, null, null, pageable);
                }
            }
        } else {
            if (flag != null && subject != null) {
                if (role != null) {
                    list = memberService.findByKeywordAndFlagAndSubjectAndRole("", flag, subject, role, pageable);
                } else {
                    list = memberService.findByKeywordAndFlagAndSubjectAndRole("", flag, subject, null, pageable);
                }
            } else {
                if (role != null) {
                    list = memberService.findByKeywordAndFlagAndSubjectAndRole("", null, null, role, pageable);
                } else {
                    list = memberService.memberList(pageable);
                }
            }
        }
        int startPage, endPage;
        int totalPage = list.getTotalPages();
        int nowPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        if(nowPage<=1){
            startPage = 1;
        } else {
            startPage = ((nowPage - 1) / 5) * 5 + 1;
        }
        endPage = startPage + 5 - 1;
        if(endPage > totalPage) {
            endPage = totalPage-1;
        }

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPage", totalPage);

        return "admin/member/list2";
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
    public String member_read(Model model,Long no){
        MemberDTO member = memberService.member_read(no);
        StudentDTO studentDTO = studentService.student_read(no);
        model.addAttribute("member", member);
        model.addAttribute("studentDTO", studentDTO);
        return "admin/member/read";
    }

    @PostMapping("/change_status")
    public String out1(String id, Model model, Status status){
        MemberDTO memberDTO = memberService.loginId(id);
        memberDTO.setStatus(status);
        memberService.member_edit(memberDTO);
        Integer cno = memberDTO.getCourse().getNo();
        return "redirect:/admin/member?cno="+cno;
    }

    @PostMapping("/change_role")
    public String role1(String id, Model model, Role role){
        MemberDTO memberDTO = memberService.loginId(id);
        memberDTO.setRole(role);
        memberService.member_edit(memberDTO);
        Integer cno = memberDTO.getCourse().getNo();
        return "redirect:/admin/member?cno="+ cno;
    }

    @PostMapping("pw_reset")
    public String pw_reset(@RequestBody Long no){
        log.info("pw_reset start --------------");
        log.info("no --------------" + no);
        memberService.pw_reset(no);
        return  "redirect:/admin/member_read?no="+no;
    }


    @PostMapping("student_edit")
    public String student_edit(StudentDTO studentDTO){
        studentService.student_edit(studentDTO);
        Long no = studentDTO.getNo();
        return  "redirect:/admin/member_read?no="+ no;
    }




}