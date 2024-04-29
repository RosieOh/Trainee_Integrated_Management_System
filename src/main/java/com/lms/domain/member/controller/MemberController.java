package com.lms.domain.member.controller;

import com.lms.domain.Course.dto.CourseDTO;
import com.lms.domain.board.dto.BoardDTO;
import com.lms.domain.board.service.BoardService;
import com.lms.domain.file.dto.FileDTO;
import com.lms.domain.file.service.FileService;
import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.service.MemberService;
import com.lms.domain.student.dto.StudentDTO;
import com.lms.domain.student.service.StudentService;
import com.lms.global.cosntant.Subject;
import com.lms.global.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.security.Principal;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/member/")
public class MemberController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final StudentService studentService;
    private final FileService fileService;


    @GetMapping("/index")
    public String index(Principal principal, Model model) {
        String id = principal.getName();
        MemberDTO memberDTO = memberService.loginId(id);
        model.addAttribute("memberDTO",memberDTO);

        log.info(memberDTO.toString());
        String courseName ="관리자";
        log.info(memberDTO.getCourse().getSubject());
        if (memberDTO.getCourse().getSubject() == Subject.BIGDATA) {
            courseName = "프로젝트 기반 빅데이터 서비스 개발자 양성 " + memberDTO.getCourse().getFlag()+"기";
        } else if ( memberDTO.getCourse().getSubject() == Subject.FULLSTACK) {
            courseName = "에듀테크 풀스택 개발자 양성(Java) " + memberDTO.getCourse().getFlag()+"기";
        } else if ( memberDTO.getCourse().getSubject() == Subject.FULLSTACK) {
            courseName = "에듀테크 상품서비스 PM(프로덕트매니저) 양성 " + memberDTO.getCourse().getFlag()+"기";
        } else {
            courseName = "매니저";
        }
        model.addAttribute("courseName",courseName);

        List<BoardDTO> newNoticeList = boardService.newNoticeList();
        int pinnedCount = boardService.countPinned(newNoticeList);

        model.addAttribute("pinnedCount", pinnedCount);
        model.addAttribute("memberDTO", memberDTO);
        model.addAttribute("newNoticeList", newNoticeList);

        return "user/index";
    }

    @GetMapping("/status")
    public String status(Model model, Principal principal){
        String id = principal.getName();
        int pass = memberService.loginPro(id);
        if (pass == 1) {
            model.addAttribute("msg", "환영합니다! 로그인되었습니다");
            model.addAttribute("url", "/member/index");
            return "user/alert";
        } else if (pass == 2) {
            model.addAttribute("msg", "해당 학생은 수강철회하였습니다. 관리자에게 문의해주세요.");
            model.addAttribute("url", "/logout");
            return "user/alert";
        } else if (pass==3){
            model.addAttribute("msg", "해당 학생은 중도포기하였습니다. 관리자에게 문의해주세요.");
            model.addAttribute("url", "/logout");
            return "user/alert";
        } else if (pass==4){
            model.addAttribute("msg", "처음 오신걸 환영합니다 ^^");
            model.addAttribute("url", "/member/mypage2");
            return "user/alert";
        } else {
            model.addAttribute("msg", "로그인 정보가 맞지 않습니다.");
            model.addAttribute("url", "/login");
            return "user/alert";
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
        log.info("getPicture ㅡㅡㅡㅡ" + studentDTO.getPicture());
        log.info("getPortfolio ㅡㅡㅡㅡ" + studentDTO.getPortfolio());
        log.info("getResume ㅡㅡㅡㅡ" + studentDTO.getResume());

        if(studentDTO.getPicture() != null){
            FileDTO picture_file = fileService.getFile(studentDTO.getPicture());
            model.addAttribute("picture_file", picture_file);

        }
        if(studentDTO.getPortfolio() != null){
            FileDTO Portfolio_file = fileService.getFile(studentDTO.getPortfolio());
            model.addAttribute("Portfolio_file", Portfolio_file);
        }
        if(studentDTO.getResume() != null){
            FileDTO resume_file = fileService.getFile(studentDTO.getResume());
            model.addAttribute("resume_file", resume_file);
        }
        model.addAttribute("studentDTO", studentDTO);
        return "user/member/mypage2";
    }

    @PostMapping("member_edit")
    public String member_edit(MemberDTO memberDTO, Integer cno){
        CourseDTO course = new CourseDTO();
        course.setNo(cno);
        memberDTO.setCourse(course);
        memberService.member_edit(memberDTO);
        return "redirect:/member/mypage";
    }

    @PostMapping("student_add")
    public String mypage2(StudentDTO studentDTO, Model model, Principal principal, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          @RequestParam("picture") MultipartFile picture,
                          @RequestParam("portfolio") MultipartFile portfolio,
                          @RequestParam("resume") MultipartFile resume){
        try {
            if(picture != null) {
                String picture_origin = picture.getOriginalFilename();
                String picture_filename = new MD5Generator(picture_origin).toString();
                String picture_savePath = System.getProperty("user.dir") + "/files/";
                String picture_filePath = picture_filename + picture_savePath;
                picture.transferTo(new File(picture_filePath));
                FileDTO fileDTO = new FileDTO();
                fileDTO.setOriginFileName(picture_origin);
                fileDTO.setFileName(picture_filename);
                fileDTO.setFilePath(picture_savePath);
                Long fileId = fileService.saveFile(fileDTO);
                studentDTO.setPicture(fileId);
            }
            if(portfolio != null) {
                String portfolio_origin = portfolio.getOriginalFilename();
                String portfolio_filename = new MD5Generator(portfolio_origin).toString();
                String portfolio_savePath = System.getProperty("user.dir") + "/files/";
                String portfolio_filePath = portfolio_filename + portfolio_savePath;
                portfolio.transferTo(new File(portfolio_filePath));
                FileDTO fileDTO2 = new FileDTO();
                fileDTO2.setOriginFileName(portfolio_origin);
                fileDTO2.setFileName(portfolio_filename);
                fileDTO2.setFilePath(portfolio_savePath);
                Long fileId2 = fileService.saveFile(fileDTO2);
                studentDTO.setPortfolio(fileId2);
            }
            if(resume != null) {
                String resume_origin = resume.getOriginalFilename();
                String resume_filename = new MD5Generator(resume_origin).toString();
                String resume_savePath = System.getProperty("user.dir") + "/files/";
                String resume_filePath = resume_filename + resume_savePath;
                resume.transferTo(new File(resume_filePath));
                FileDTO fileDTO3 = new FileDTO();
                fileDTO3.setOriginFileName(resume_origin);
                fileDTO3.setFileName(resume_filename);
                fileDTO3.setFilePath(resume_savePath);
                Long fileId3 = fileService.saveFile(fileDTO3);
                studentDTO.setResume(fileId3);
            }

//            if(!new File(picture_savePath).exists()) {
//                try {
//                    new File(picture_savePath).mkdirs();
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            if(!new File(portfolio_savePath).exists()) {
//                try {
//                    new File(portfolio_savePath).mkdirs();
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            if(!new File(resume_savePath).exists()) {
//                try {
//                    new File(resume_savePath).mkdirs();
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }

        studentService.student_edit(studentDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        return "redirect:/member/mypage2";
    }
}