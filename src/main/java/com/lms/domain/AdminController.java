
package com.lms.domain;

import com.lms.domain.Course.dto.CourseDTO;
import com.lms.domain.Course.service.CourseService;
import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.dto.MemberVO;
import com.lms.domain.member.entity.Member;
import com.lms.domain.member.service.MemberService;
import com.lms.domain.student.dto.StudentDTO;
import com.lms.domain.student.service.StudentService;
import com.lms.global.cosntant.Role;
import com.lms.global.cosntant.Status;
import com.lms.global.cosntant.Subject;
import com.lms.global.util.PageDTO;
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

    // 회원관리 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ


    // 회원 리스트(관리자)
    @GetMapping("/admin_member")
    public String admin_member(Model model, Principal principal,HttpServletRequest request, @PageableDefault(page=0, size=20, sort="name", direction= Sort.Direction.ASC)Pageable pageable,
                               @RequestParam(required = false) String keyword, @RequestParam(required = false)Subject subject, @RequestParam(required = false)Integer flag, @RequestParam(required = false)Role role){

//        List<MemberDTO> memberList = memberService.member_list();
//        model.addAttribute("memberList",memberList);

        List<CourseDTO> course_big_List = courseService.course_subject_list(Subject.BIGDATA);
        List<CourseDTO> course_full_List = courseService.course_subject_list(Subject.FULLSTACK);
        List<CourseDTO> course_pm_List = courseService.course_subject_list(Subject.PM);
        Subject[] subjects = {Subject.BIGDATA, Subject.FULLSTACK, Subject.PM};
        Role[] roles = {Role.STUDENT, Role.TEACHER};

        model.addAttribute("course_big_List",course_big_List);
        model.addAttribute("course_full_List",course_full_List);
        model.addAttribute("course_pm_List",course_pm_List);
        model.addAttribute("subjects", subjects);
        model.addAttribute("roles", roles);

        Page<Member> members = memberService.adminSearch(keyword, flag, subject, role, pageable);
        model.addAttribute("memberList", members);
        model.addAttribute("searchTotal",members.getTotalElements());

        int pageNow = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        PageDTO<Member, MemberDTO> pageDTO = new PageDTO<>();
        pageDTO.setPageNow(pageNow);
        pageDTO.setPostTotal(members.getTotalElements());
        pageDTO.setPageTotal(members.getTotalPages());
        pageDTO.build(members);
        pageDTO.entity2dto(members, MemberDTO.class);

        model.addAttribute("pageDTO", pageDTO);

        return "admin/member/admin_member";
    }

    // 회원 리스트(매니저)
    @GetMapping("/member")
    public String memberList(HttpServletRequest request, Model model, @PageableDefault(page=0, size=20, sort="name", direction= Sort.Direction.ASC)Pageable pageable,
                             @RequestParam(required = false) String keyword, @RequestParam(required = false) Subject subject, @RequestParam(required = false)Integer flag, @RequestParam(required = false)Role role) {

        List<CourseDTO> course_big_List = courseService.course_subject_list(Subject.BIGDATA);
        List<CourseDTO> course_full_List = courseService.course_subject_list(Subject.FULLSTACK);
        List<CourseDTO> course_pm_List = courseService.course_subject_list(Subject.PM);
        Subject[] subjects = {Subject.BIGDATA, Subject.FULLSTACK, Subject.PM};
        Role[] roles = {Role.STUDENT, Role.TEACHER};

        model.addAttribute("course_big_List",course_big_List);
        model.addAttribute("course_full_List",course_full_List);
        model.addAttribute("course_pm_List",course_pm_List);
        model.addAttribute("subjects", subjects);
        model.addAttribute("roles", roles);

        Page<Member> members = memberService.managerSearch(keyword, flag, subject, role, pageable);
        model.addAttribute("memberList", members);
        model.addAttribute("searchTotal", members.getTotalElements());

        int pageNow = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        PageDTO<Member, MemberDTO> pageDTO = new PageDTO<>();
        pageDTO.setPageNow(pageNow);
        pageDTO.setPostTotal(members.getTotalElements());
        pageDTO.setPageTotal(members.getTotalPages());
        pageDTO.build(members);
        pageDTO.entity2dto(members, MemberDTO.class);
        model.addAttribute("pageDTO", pageDTO);
        return "admin/member/list";
    }


    // 회원 상세보기
    @GetMapping("/member_read")
    public String member_read(Model model,Long no){
        MemberDTO member = memberService.member_read(no);
        StudentDTO studentDTO = studentService.student_read(no);
        model.addAttribute("member", member);
        model.addAttribute("studentDTO", studentDTO);
        return "admin/member/read";
    }

    // 회원 상태변경
    @PostMapping("/change_status")
    public String out1(String id, Model model, Status status){
        MemberDTO memberDTO = memberService.loginId(id);
        memberDTO.setStatus(status);
        memberService.member_edit(memberDTO);
        Integer cno = memberDTO.getCourse().getNo();
        return "redirect:/admin/member?cno="+cno;
    }

    // 회원 권한변경
    @PostMapping("/change_role")
    public String role1(String id, Model model, Role role){
        MemberDTO memberDTO = memberService.loginId(id);
        memberDTO.setRole(role);
        memberService.member_edit(memberDTO);
        Integer cno = memberDTO.getCourse().getNo();
        return "redirect:/admin/member?cno="+ cno;
    }

    // 관리자 회원 상태변경
    @PostMapping("/admin_change_status")
    public String admin_change_status(String id, Model model, Status status){
        MemberDTO memberDTO = memberService.loginId(id);
        memberDTO.setStatus(status);
        memberService.member_edit(memberDTO);
        return "redirect:/admin/admin_member";
    }

    // 관리자 회원 권한변경
    @PostMapping("/admin_change_role")
    public String admin_change_role(String id, Model model, Role role){
        MemberDTO memberDTO = memberService.loginId(id);
        memberDTO.setRole(role);
        memberService.member_edit(memberDTO);
        return "redirect:/admin/admin_member";
    }

    // 관리자 회원 클래스변경
    @PostMapping("/admin_change_course")
    public String admin_change_course(String id, Model model, Role role, Integer cno){
        MemberDTO memberDTO = memberService.loginId(id);
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setNo(cno);
        memberDTO.setCourse(courseDTO);
        memberService.member_edit(memberDTO);
        return "redirect:/admin/admin_member";
    }

    // 관리자 회원가입
    @PostMapping("/admin_joinPro")
    public String joinPro(MemberDTO memberDTO, @RequestParam("cno") Integer cno){
        CourseDTO course = new CourseDTO();
        course.setNo(cno);
        memberDTO.setCourse(course);
        memberDTO.setStatus(Status.ACTIVE);
        memberDTO.setRole(Role.MANAGER);
        memberService.member_add(memberDTO);
        MemberDTO memberDTO1 = memberService.loginId(memberDTO.getId());
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setNo(memberDTO1.getNo());
        studentService.student_add(studentDTO);
        return "redirect:/admin/admin_member";
    }


    // 비밀번호 초기화(진행중)
    @PostMapping("pw_reset")
    public String pw_reset(@RequestBody Long no){
        log.info("pw_reset start --------------");
        log.info("no --------------" + no);
        memberService.pw_reset(no);
        return  "redirect:/admin/member_read?no="+no;
    }

    // 회원 추가사항 변경
    @PostMapping("student_edit")
    public String student_edit(StudentDTO studentDTO){
        studentService.student_edit(studentDTO);
        Long no = studentDTO.getNo();
        return  "redirect:/admin/member_read?no="+ no;
    }


    // 강의 시스템 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

    // 강의 리스트
    @GetMapping("/course")
    public String course(Model model){
        List<CourseDTO> courseDTOList = courseService.course_list();
        model.addAttribute("courseDTOList",courseDTOList);
        return "admin/course/list";
    }

    // 강의 등록
    @PostMapping("/coursePro")
    public String coursePro(CourseDTO courseDTO){
        courseService.course_add(courseDTO);
        return "redirect:/admin/course";
    }

    // 강의 공개여부
    @PostMapping("/course_delete")
    public String course_delete(CourseDTO courseDTO) {
        courseService.delete_type(courseDTO);
        return "redirect:/admin/course";
    }

    // 강의 변경 페이지
    @GetMapping("/course_edit")
    public String course_edit(Model model, Integer no){
        CourseDTO courseDTO = courseService.course_read(no);
        model.addAttribute("courseDTO",courseDTO);
        return "admin/course/edit";
    }

    // 강의 수정하기
    @PostMapping("/course_edit")
    public String course_editPro(Model model, CourseDTO courseDTO){
        log.info("courseDTO -----------" + courseDTO);
        courseService.course_edit(courseDTO);
        return "redirect:/admin/course";
    }


}