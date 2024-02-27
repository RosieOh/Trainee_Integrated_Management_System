package com.kdt.landing.domain.board.service;

import com.kdt.landing.domain.board.dto.BoardDTO;
import com.kdt.landing.domain.board.entity.Board;

import java.util.List;

public interface BoardService {

    public BoardDTO findById(Long id);

    public List<BoardDTO> findAll(BoardDTO boardDTO);

    public void register(BoardDTO boardDTO);

    public void modify(BoardDTO boardDTO);

    public void remove(Long id);

    public List<BoardDTO> findByBoardType(String boardType);
}
