package com.kdt.landing.domain.board.service;


import com.kdt.landing.domain.board.dto.BoardDTO;
import com.kdt.landing.domain.board.entity.Board;
import com.kdt.landing.domain.board.repository.BoardRepository;
import com.kdt.landing.global.cosntant.BoardType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;

    // 새로운 게시판을 추가해주는 메소드
    public void createAndSaveBoards() {
        List<Board> board = new ArrayList<>();

        Board noticeBoard = new Board();
        noticeBoard.setTitle("공지사항");
        noticeBoard.setContent("내용");
        noticeBoard.setBoardType(BoardType.NOTICE.toString());
        board.add(noticeBoard);

        // 생성된 게시판들을 저장
        boardRepository.saveAll(board);
    }
    @Override
    public BoardDTO findById(Long id) {
        Optional<Board> result = boardRepository.findById(id);
        log.info("문제 찾기 " + result.toString() );
        BoardDTO boardDTO = modelMapper.map(result, BoardDTO.class);
        log.info("문제 찾기 " + boardDTO.toString() );
        return boardDTO;
    }

    @Override
    public List<BoardDTO> findAll(BoardDTO boardDTO) {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();

        for(Board board1 : boardList) {
            Board board = Board.builder()
                    .id(boardDTO.getId())
                    .title(boardDTO.getTitle())
                    .content(boardDTO.getContent())
                    .boardType(boardDTO.getBoardType())
                    .writer(boardDTO.getWriter())
                    .fileId(boardDTO.getFileId())
                    .build();
            boardDTOList.add(boardDTO);
        }
        return boardDTOList;
    }

    @Override
    public List<BoardDTO> findByBoardType(String boardType) {
        List<Board> boardList = boardRepository.findByBoardType(boardType);

        if (boardList != null && !boardList.isEmpty()) {
            List<BoardDTO> boardDTOList = boardList.stream().map(board -> modelMapper.map(board, BoardDTO.class))
                    .collect(Collectors.toList());
            return boardDTOList;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void register(BoardDTO boardDTO) {

        Board board = Board.builder()
                .id(boardDTO.getId())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .boardType(boardDTO.getBoardType())
                .writer(boardDTO.getWriter())
                .fileId(boardDTO.getFileId())
                .build();
        boardRepository.save(board);
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Optional<Board> result = boardRepository.findById(boardDTO.getId());
        Board board = result.orElseThrow();
        board.change(boardDTO.getTitle(), boardDTO.getContent());
        boardRepository.save(board);
    }

    @Override
    public void remove(Long id) {
        boardRepository.deleteById(id);
    }
}
