package com.lms.domain.comment.service;

import com.lms.domain.comment.dto.CommentDTO;
import com.lms.domain.comment.entity.Comment;

import java.util.List;

public interface CommentService {
    public List<CommentDTO> findCommentList(Long bid);
    public void commentInsert(CommentDTO commentDTO);
    public CommentDTO getComment(Long id);
    public void commentRemove(CommentDTO commentDTO);
}
