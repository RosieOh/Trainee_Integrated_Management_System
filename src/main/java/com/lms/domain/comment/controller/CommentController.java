package com.lms.domain.comment.controller;

import com.lms.domain.comment.dto.CommentDTO;
import com.lms.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/commentInsert")
    public String commentInsert(@Valid CommentDTO commentDTO) {
        //댓글 입력 시 필요한 것 bid,content, writer
        commentDTO.setBid(commentDTO.getBid());
        commentDTO.setWriter(commentDTO.getWriter());
        commentDTO.setContent(commentDTO.getContent());
        commentService.commentInsert(commentDTO);

        return "redirect:/notice/class/read?id="+commentDTO.getBid();
    }

    @PostMapping("/commentRemove")
    public String commentRemove(Long id, boolean deleteType) {
        CommentDTO commentDTO = commentService.getComment(id);
        commentDTO.setDeleteType(deleteType);
        commentService.commentRemove(commentDTO);
        return "redirect:/notice/class/read?id="+commentDTO.getBid();
    }
}
