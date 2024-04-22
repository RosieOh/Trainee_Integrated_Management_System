package com.lms.domain.index;

import com.lms.domain.board.dto.BoardDTO;
import com.lms.domain.board.repository.BoardRepository;
import com.lms.domain.board.service.BoardService;
import com.lms.domain.member.service.MemberService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lms.domain.board.entity.Board;


import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class IndexController {

    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/index4")
    public String index4(Model model) {
        List<BoardDTO> newNoticeList = boardService.newNoticeList();
        model.addAttribute("newNoticeList",newNoticeList);
        return "user/index4";
    }
}
