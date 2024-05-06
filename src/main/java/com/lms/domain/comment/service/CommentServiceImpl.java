package com.lms.domain.comment.service;

import com.lms.domain.comment.dto.CommentDTO;
import com.lms.domain.comment.entity.Comment;
import com.lms.domain.comment.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;


@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    @Override
    public void commentInsert(CommentDTO commentDTO) {
        Comment comment = Comment.builder()
                .id(commentDTO.getId())
                .bid(commentDTO.getBid())
                .content(commentDTO.getContent())
                .writer(commentDTO.getWriter())
                .build();
        commentRepository.save(comment);
    }

    @Override
    public List<CommentDTO> findCommentList(Long bid) {
        List<Comment> commentList= commentRepository.findAll();
        List<CommentDTO> commentDTOList = commentList.stream()
                .filter(comment -> comment.getBid().equals(bid)) // bid가 같은 것만 필터링
                .filter(comment -> comment.isDeleteType() == false) // delete_type이 0인 것만 필터링
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
        return commentDTOList;
    }
    @Override
    public CommentDTO getComment(Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        CommentDTO commentDTO = commentOptional.map(comment -> modelMapper.map(comment, CommentDTO.class))
                .orElse(null);
        return commentDTO;
    }
    @Override
    public void commentRemove(CommentDTO commentDTO) {
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.delete(comment.isDeleteType());
        commentRepository.save(comment);
    }
}
