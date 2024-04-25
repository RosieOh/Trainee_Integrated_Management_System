package com.lms.global.util;

import com.lms.domain.board.entity.Board;
import com.lms.domain.board.entity.QBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;


public class SearchRepoImpl extends QuerydslRepositorySupport implements SearchRepo {

    public SearchRepoImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> searchPage(Pageable pageable, PageDTO pageDTO) {
        QBoard board = QBoard.board;

        JPQLQuery<Board> query = from(board);

        String[] types = pageDTO.getTypes();
        String keyword = pageDTO.getKeyword();

        if(types!=null && keyword!=null && !keyword.isEmpty()){
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for(String type: types){
                System.out.println(type);
                switch(type){
                    case "title":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "content":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "writer":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }


        this.getQuerydsl().applyPagination(pageable, query);
        List<Board> list = query.fetch();

        return new PageImpl<>(list, pageable, query.fetchCount());
    }
}
