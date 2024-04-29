package com.lms.domain.board.service;

import com.lms.domain.board.dto.BoardDTO;
import com.lms.domain.board.entity.Board;
import com.lms.domain.member.entity.Member;
import com.lms.global.cosntant.Role;
import com.lms.global.cosntant.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {

    public BoardDTO findById(Long id);
    //public List<BoardDTO> findAll(BoardDTO boardDTO);
    List<BoardDTO> findNoticeAll();

    // test
    public List<Board> boardList();

    public void register(BoardDTO boardDTO);

    public void modify(BoardDTO boardDTO);

    public void remove(Long id);

    public BoardDTO getBoard(Long id);

    public List<BoardDTO> findByBoardType(String boardType);

    //메인 인덱스에 최신 공지사항 5개 불러오기
    public List<BoardDTO> newNoticeList();

    public int countPinned(List<BoardDTO> boardDTOList);

    //-------------------클래스 공지사항---------------------------
    List<BoardDTO> classNoticeAll(Long cno); //클래스 별 공지사항 리스트

    //검색 기능 및 페이징 추가
    public Page<Board> searchNotice(String keyword, Integer cno, Pageable pageable);
    public int countPinnedPaging(Page<Board> pagingPinList);


}
