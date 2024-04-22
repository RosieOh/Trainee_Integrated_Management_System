package com.lms.domain.index;

import com.lms.domain.board.dto.BoardDTO;
import com.lms.domain.board.repository.BoardRepository;
import com.lms.domain.board.service.BoardService;
import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.service.MemberService;
import com.lms.global.cosntant.Subject;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lms.domain.board.entity.Board;


import java.security.Principal;
import java.util.List;

@Slf4j
@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class IndexController {

    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/index4")
    public String index4(Model model, Principal principal) {
        String id = principal.getName();
        MemberDTO memberDTO = memberService.loginId(id);
        model.addAttribute("memberDTO",memberDTO);

        String courseName ="관리자";
        log.info(memberDTO.getCourse().getSubject());
        if (memberDTO.getCourse().getSubject() == Subject.BIGDATA) {
            courseName = "프로젝트 기반 빅데이터 서비스 개발자 양성 " + memberDTO.getCourse().getFlag()+"기";
        } else if ( memberDTO.getCourse().getSubject() == Subject.FULLSTACK) {
            courseName = "에듀테크 풀스택 개발자 양성(Java) " + memberDTO.getCourse().getFlag()+"기";
        } else {
            courseName = "에듀테크 상품서비스 PM(프로덕트매니저) 양성 " + memberDTO.getCourse().getFlag()+"기";
        }
        model.addAttribute("courseName",courseName);

        List<BoardDTO> newNoticeList = boardService.newNoticeList();
        model.addAttribute("newNoticeList",newNoticeList);
        return "user/index4";
    }
}
