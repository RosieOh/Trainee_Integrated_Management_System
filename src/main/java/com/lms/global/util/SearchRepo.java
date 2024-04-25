package com.lms.global.util;

import com.lms.domain.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface SearchRepo {
    Page<Board> searchPage(Pageable pageable, PageDTO pageDTO);

}
