
package com.lms.domain;

import com.lms.domain.course.dto.CourseDTO;
import com.lms.domain.course.service.CourseService;
import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.service.MemberService;
import com.lms.domain.student.dto.StudentDTO;
import com.lms.domain.student.service.StudentService;
import com.lms.global.cosntant.Role;
import com.lms.global.cosntant.Status;
import com.lms.global.cosntant.Subject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final MemberService memberService;
    private final CourseService courseService;
    private final StudentService studentService;


    @GetMapping("/")
    public String home() {
            memberService.createAdminMember(); // 관리자 회원 생성 메서드 호출
        return "user/member/login";
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
        MemberDTO memberDTO1 = memberService.loginId(memberDTO.getId());
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setNo(memberDTO1.getNo());
        studentService.student_add(studentDTO);
        return "redirect:/";
    }

    @PostMapping("idCheckPro")
    public ResponseEntity idCheck(@RequestBody MemberDTO memberDTO) throws Exception {
        String id = memberDTO.getId();
        boolean result = memberService.id_check(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 본인확인
    @GetMapping("/pw")
    public String pw_confirm (Model model) { return "user/pw_validate"; }

    //비밀번호변경
    @GetMapping("/pw2")
    public String pw_modify (Model model) { return "user/pw_modify"; }


}