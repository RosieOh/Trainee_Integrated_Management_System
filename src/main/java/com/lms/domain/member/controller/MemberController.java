package com.lms.domain.member.controller;

import com.lms.domain.board.dto.BoardDTO;
import com.lms.domain.board.service.BoardService;
import com.lms.domain.course.dto.CourseDTO;
import com.lms.domain.file.dto.FileDTO;
import com.lms.domain.file.service.FileService;
import com.lms.domain.fileStudent.dto.FileStudentDTO;
import com.lms.domain.fileStudent.service.FileStudentService;
import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.service.MemberService;
import com.lms.domain.student.dto.StudentDTO;
import com.lms.domain.student.service.StudentService;
import com.lms.global.cosntant.Subject;
import com.lms.global.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/member/")
public class MemberController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final StudentService studentService;
    private final FileStudentService fileStudentService;
    private final FileService fileService;


    @GetMapping("/index")
    public String index(Principal principal, Model model) {
        String id = principal.getName();
        MemberDTO memberDTO = memberService.loginId(id);
        StudentDTO studentDTO = studentService.student_read(memberDTO.getNo());
        String student_picture = null;
        if(studentDTO.getPicture() != null) {
            student_picture = studentDTO.getNo() + "/" + studentDTO.getPicture();
        }

        log.info(memberDTO.toString());
        String courseName ="관리자";
        log.info(memberDTO.getCourse().getSubject());
        if (memberDTO.getCourse().getSubject() == Subject.BIGDATA) {
            courseName = "프로젝트 기반 빅데이터 서비스 개발자 양성 " + memberDTO.getCourse().getFlag()+"기";
        } else if ( memberDTO.getCourse().getSubject() == Subject.FULLSTACK) {
            courseName = "에듀테크 풀스택 개발자 양성(Java) " + memberDTO.getCourse().getFlag()+"기";
        } else if ( memberDTO.getCourse().getSubject() == Subject.PM) {
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

        List<FileDTO> fileList = new ArrayList<>();
        for (BoardDTO board : newNoticeList) {
            List<FileDTO> fileDTOs = fileService.findByBoardId(board.getId());
            for (FileDTO fileDTO : fileDTOs) {
                fileDTO.setBoardId(board.getId());
            }
            fileList.addAll(fileDTOs);
        }

        model.addAttribute("student_picture", student_picture);
        model.addAttribute("memberDTO",memberDTO);
        model.addAttribute("fileList", fileList);
        model.addAttribute("fileCountMap", fileService.getFileCountMap(fileList));

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
        MemberDTO member = memberService.loginId(principal.getName());
        String courseName ="관리자";
        if (member.getCourse().getSubject() == Subject.BIGDATA) {
            courseName = "프로젝트 기반 빅데이터 서비스 개발자 양성 " + member.getCourse().getFlag()+"기";
        } else if ( member.getCourse().getSubject() == Subject.FULLSTACK) {
            courseName = "에듀테크 풀스택 개발자 양성(Java) " + member.getCourse().getFlag()+"기";
        } else if ( member.getCourse().getSubject() == Subject.PM) {
            courseName = "에듀테크 상품서비스 PM(프로덕트매니저) 양성 " + member.getCourse().getFlag()+"기";
        } else {
            courseName = "매니저";
        }
        model.addAttribute("courseName",courseName);
        model.addAttribute("member", member);
        return "user/member/mypage";
    }

    @GetMapping("mypage2")
    public String mypage2(Model model, Principal principal){
        String id = principal.getName();
        MemberDTO memberDTO = memberService.loginId(id);
        StudentDTO studentDTO = studentService.student_read(memberDTO.getNo());

        if(studentDTO.getPicture() != null){
            FileStudentDTO picture_file = fileStudentService.getFile(studentDTO.getNo(), studentDTO.getPicture());
            model.addAttribute("picture_file", picture_file);
        }

        if(studentDTO.getPortfolio() != null){
            FileStudentDTO Portfolio_file = fileStudentService.getFile(studentDTO.getNo(), studentDTO.getPortfolio());
            model.addAttribute("Portfolio_file", Portfolio_file);
        }

        if(studentDTO.getResume() != null){
            FileStudentDTO resume_file = fileStudentService.getFile(studentDTO.getNo(), studentDTO.getResume());
            model.addAttribute("resume_file", resume_file);
        }

        model.addAttribute("studentDTO", studentDTO);
        return "user/member/mypage2";
    }

    @PostMapping("member_edit")
    public String member_edit(MemberDTO memberDTO, Principal principal){
        MemberDTO member = memberService.loginId(principal.getName());
        member.setEmail(memberDTO.getEmail());
        member.setAddr1(memberDTO.getAddr1());
        member.setPostcode(memberDTO.getPostcode());
        memberService.member_edit(member);
        return "redirect:/member/mypage";
    }

    @PostMapping("student_add")
    public String student_add(StudentDTO studentDTO, Model model, Principal principal,
                              @RequestParam("file1") MultipartFile file1,
                              @RequestParam("file2") MultipartFile file2,
                              @RequestParam("file3") MultipartFile file3){
        String randomTitle = UUID.randomUUID().toString();
        String savePath = System.getProperty("user.dir") + "/files/";

        StudentDTO studentDTO1 = studentService.student_read(studentDTO.getNo());
        studentDTO1.setStack(studentDTO.getStack());
        studentDTO1.setCert(studentDTO.getCert());
        studentDTO1.setUniv(studentDTO.getUniv());
        studentDTO1.setMajor(studentDTO.getMajor());
        studentDTO1.setHope(studentDTO.getHope());
        studentDTO1.setJob_program(studentDTO.getJob_program());
        studentDTO1.setFund(studentDTO.getFund());
        if(!new File(savePath).exists()) {
            try {
                new File(savePath).mkdirs();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            if(!file1.isEmpty()) {
                log.info("file1 시작: " + file1);
                String picture_origin = file1.getOriginalFilename();
                String picture_saveFileName = new MD5Generator(picture_origin).toString();

                file1.transferTo(new File(savePath, picture_saveFileName));
                FileStudentDTO fileDTO = new FileStudentDTO();
                fileDTO.setOriginFileName(picture_origin);
                fileDTO.setSaveFileName(picture_saveFileName);
                fileDTO.setSavePath(savePath);
                fileDTO.setMemberId(studentDTO.getNo());
                Long no = fileStudentService.saveFile(fileDTO);
                studentDTO1.setPicture(no);

            }
            if(!file2.isEmpty()) {
                String portfolio_origin = file2.getOriginalFilename();
                String portfolio_saveFileName = new MD5Generator(portfolio_origin).toString();

                file2.transferTo(new File(savePath, portfolio_saveFileName));
                FileStudentDTO fileDTO = new FileStudentDTO();
                fileDTO.setOriginFileName(portfolio_origin);
                fileDTO.setSaveFileName(portfolio_saveFileName);
                fileDTO.setSavePath(savePath);
                fileDTO.setMemberId(studentDTO.getNo());
                Long no = fileStudentService.saveFile(fileDTO);
                studentDTO1.setPortfolio(no);

            }
            if(!file3.isEmpty()) {
                String resume_origin = file3.getOriginalFilename();
                String resume_saveFileName = new MD5Generator(resume_origin).toString();

                file3.transferTo(new File(savePath, resume_saveFileName));
                FileStudentDTO fileDTO = new FileStudentDTO();
                fileDTO.setOriginFileName(resume_origin);
                fileDTO.setSaveFileName(resume_saveFileName);
                fileDTO.setSavePath(savePath);
                fileDTO.setMemberId(studentDTO.getNo());
                Long no = fileStudentService.saveFile(fileDTO);
                studentDTO1.setResume(no);
            }
            studentService.student_edit(studentDTO1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        return "redirect:/member/mypage2";
    }

    @GetMapping("file_edit")
    public String FaqEditForm(Model model, int no) {
        model.addAttribute("no", no);
        return "user/member/studentEdit";
    }

    @PostMapping(value = "file_edit", consumes = { "multipart/form-data"} )
    public String file_edit(@RequestParam("fileChange") MultipartFile files, Model model, @RequestParam("no") int no, Principal principal) throws Exception{
        MemberDTO memberDTO = memberService.loginId(principal.getName());
        StudentDTO studentDTO = studentService.student_read(memberDTO.getNo());

        try {
            String savePath = System.getProperty("user.dir") + "/files/";
            String originName = files.getOriginalFilename();
            String saveFileName = new MD5Generator(originName).toString();

            if (no == 1) {
                // 프로필 사진
                FileStudentDTO fileDTO = fileStudentService.getFile(memberDTO.getNo(), studentDTO.getPicture());
                File fileOne = new File(fileDTO.getSavePath(), fileDTO.getSaveFileName());
                boolean deleteFile = fileOne.delete();
                fileDTO.setOriginFileName(originName);
                fileDTO.setSaveFileName(saveFileName);
                files.transferTo(new File(savePath, saveFileName));
                fileStudentService.saveFile(fileDTO);

            } else if(no == 2) {
                // 포트폴리오
                FileStudentDTO fileDTO = fileStudentService.getFile(memberDTO.getNo(), studentDTO.getPortfolio());
                File fileOne = new File(fileDTO.getSavePath(), fileDTO.getSaveFileName());
                boolean deleteFile = fileOne.delete();
                fileDTO.setOriginFileName(originName);
                fileDTO.setSaveFileName(saveFileName);
                files.transferTo(new File(savePath, saveFileName));
                fileStudentService.saveFile(fileDTO);

            } else if(no == 3) {
                // 이력서
                FileStudentDTO fileDTO = fileStudentService.getFile(memberDTO.getNo(), studentDTO.getResume());
                File fileOne = new File(fileDTO.getSavePath(), fileDTO.getSaveFileName());
                boolean deleteFile = fileOne.delete();
                fileDTO.setOriginFileName(originName);
                fileDTO.setSaveFileName(saveFileName);
                files.transferTo(new File(savePath, saveFileName));
                fileStudentService.saveFile(fileDTO);
            }

        } catch (Exception e) {
            log.info("오류 시작");
            e.printStackTrace();
        }
        model.addAttribute("url", 1);
        return "user/alert";
    }


    @GetMapping("validatePw")
    public String validatePwForm(){
        return "user/member/pw_validate";
    }

    @PostMapping("pwCheck")
    public ResponseEntity pwCheck(@RequestBody MemberDTO memberDTO, Principal principal) throws Exception {
        boolean result = memberService.validatePw(principal.getName(), memberDTO.getPw());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("changePw")
    public String changePwForm(){
        return "user/member/pw_change";
    }

    @PostMapping("changePw")
    public String changePw(Model model, Principal principal, @RequestParam("pw") String pw){
        memberService.changePw(principal.getName(), pw);
        model.addAttribute("msg", "비밀번호가 변경되었습니다.");
        model.addAttribute("url", "/member/mypage");
        return "user/alert";
    }

}