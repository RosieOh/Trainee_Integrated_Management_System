package com.lms.domain.board.service;


import com.lms.domain.Course.repository.CourseRepository;
import com.lms.domain.board.dto.BoardDTO;
import com.lms.domain.board.entity.Board;
import com.lms.domain.board.entity.QBoard;
import com.lms.domain.board.repository.BoardRepository;
import com.lms.domain.member.entity.Member;
import com.lms.domain.member.entity.QMember;
import com.lms.global.cosntant.BoardType;
import com.lms.global.cosntant.Subject;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final ModelMapper modelMapper;
    private final CourseRepository courseRepository;
    private final BoardRepository boardRepository;
    private final JPAQueryFactory queryFactory;

    // 새로운 게시판을 추가해주는 메소드
    public void createAndSaveBoards() {
        List<Board> board = new ArrayList<>();

        // 인스펙션 필요
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
        BoardDTO boardDTO = modelMapper.map(result, BoardDTO.class);
        return boardDTO;
    }

    @Override
    public List<BoardDTO> findNoticeAll() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = boardList.stream()
//                .filter(board -> board.getCno() == 0)
                .map(board -> modelMapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());

        return boardDTOList;
    }

    @Override
    public List<Board> boardList() {
        return boardRepository.findAll();
    }

    @Override
    public List<BoardDTO> findByBoardType(String boardType) {
        List<Board> lst = boardRepository.findByBoardType(boardType);
        if (lst != null && !lst.isEmpty()) {
            List<BoardDTO> boardList = lst.stream().map(board -> modelMapper.map(board, BoardDTO.class))
                    .collect(Collectors.toList());
            return boardList;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void register(BoardDTO boardDTO) {
        log.info(boardDTO.getBoardType());
        Board board = Board.builder()
                .id(boardDTO.getId())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .boardType(boardDTO.getBoardType())
                .writer(boardDTO.getWriter())
                .fileId(boardDTO.getFileId())
                .pinned(boardDTO.isPinned())
                .privated(boardDTO.isPrivated())
                .cno(boardDTO.getCno())
                .build();
        boardRepository.save(board);
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Board board = modelMapper.map(boardDTO, Board.class);
        board.change(boardDTO.getTitle(), boardDTO.getContent(), boardDTO.isPinned(), boardDTO.isPrivated());
        boardRepository.save(board);
    }


    @Override
    public BoardDTO getBoard(Long id ) {
        Optional<Board> result = boardRepository.findById(id);
        Board board = result.orElseThrow();
        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);
        return boardDTO;
    }



    @Override
    public void remove(Long id) {
        boardRepository.deleteById(id);
    }

    //메인 인덱스에 최신 공지사항 5개 불러오기
    @Override
    public List<BoardDTO> newNoticeList() {
        List<Board> notices = boardRepository.newNoticeList();

        // 엔티티 내용 출력
        for (Board notice : notices) {
            log.info("Notice: " + notice.getTitle());
        }
        List<BoardDTO> newNoticeList = notices.stream()
                .filter(board -> board.getCno() == 0)
                .map(board -> modelMapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());
        return newNoticeList;
    }

    @Override
    public int countPinned(List<BoardDTO> boardDTOList) {
        int pinnedCount =0;
        for (BoardDTO board : boardDTOList) {
            if (board.isPinned()) {
                pinnedCount++;
            }
        }
        return pinnedCount;
    }

    //----------------------------클래스 공지사항-----------------------

    @Override
    public List<BoardDTO> classNoticeAll(Long cno) {
        List<Board> boardList = boardRepository.findAll();
        if (cno == 1) {
            List<BoardDTO> boardDTOList = boardList.stream()
                    .filter(board -> board.getCno() != 0)
                    .map(board -> modelMapper.map(board, BoardDTO.class))
                    .collect(Collectors.toList());
            return boardDTOList;

        } else {
            List<BoardDTO> boardDTOList = boardList.stream()
                    .filter(board -> board.getCno() == cno)
                    .map(board -> modelMapper.map(board, BoardDTO.class))
                    .collect(Collectors.toList());
            return boardDTOList;
        }


    }

    @Override
    public Page<Board> searchNotice(String keyword, Integer cno, Pageable pageable) {
        BooleanBuilder where = new BooleanBuilder();

        // 키워드가 있는 경우 이름 필터링
        if (StringUtils.hasText(keyword)) {
            where.and(QBoard.board.title.containsIgnoreCase(keyword));
        }

        if (cno != null) {
            where.and(QBoard.board.cno.eq(Long.valueOf(cno)));
        }


        // 페이징 처리
        JPAQuery<Board> query = queryFactory
                .selectFrom(QBoard.board)
                .where(where)
                .orderBy(QBoard.board.pinned.desc(), QBoard.board.createdTime.desc());

        // 페이징 처리된 결과 반환
        QueryResults<Board> results = query
                .offset(pageable.getOffset()) // 오프셋 설정
                .limit(pageable.getPageSize()) // 페이지 크기 설정
                .fetchResults(); // 결과 가져오기

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public int countPinnedPaging(Page<Board> pagingPinList) {
        int PinnedPaging =0;
        for (Board board : pagingPinList) {
            if (board.isPinned()) {
                PinnedPaging++;
            }
        }
        return PinnedPaging;
    }

}
