package com.lms.global.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO<E, T> {

    private static final ModelMapper modelMapper = new ModelMapper();

    //페이지에서 보여지는 번호 개수
    private int pageScreen = 5;
    //페이지에서 보여지는 번호 중 첫번째 번호
    private int pageStart;
    //페이지에서 보여지는 번호 중 마지막 번호
    private int pageLast;
    //처음 들어갔을 때 페이지에서 눌러져있는 번호 혹은 내가 누른 번호
    private int pageNow = 1;
    //전체 페이지 개수
    private int pageTotal;
    //페이지에서 한 번호당 게시글 목록 수
    private int postScreen = 20;
    //총 게시글 수
    private Long postTotal;

    //페이지에 넣어져있는 데이터 (나중에 저희 페이지의 보드 리스트를 이 메소드로 불러옵니다.)
    private List<T> listDTO;


    //JPA에서 제공하는 메소드 Pageable pageable = PageRequest.of(PAGE, SIZE);
    //PAGE 는 페이지의 순서 (index), SIZE 는 현재 페이지의 크기
    public Pageable getPageable(){
        return PageRequest.of(this.pageNow-1, this.postScreen);
    }


    //페이징 처리 메소드 ( 시작 페이지, 마지막 페이지에 대한 처리 )
    public void build(Page<E> result) {
        this.postTotal = result.getTotalElements();
        this.pageTotal = result.getTotalPages();

        // 시작 페이지 번호 설정
        // 현재 페이지가 0 또는 1인 경우 시작 페이지를 1로 제한
        this.pageStart = Math.max(1, (this.pageNow / this.pageScreen) * this.pageScreen+1 );

        // 마지막 페이지 번호 설정
        this.pageLast = this.pageStart + this.pageScreen-1;
        // 마지막 페이지 번호 총 페이지 개수보다 클 때의 경우
        this.pageLast = Math.min(this.pageTotal, this.pageLast);
    }


    // 기존 엔티티 페이징 결과를 DTO 타입으로 변환
    public void entity2dto(Page<E> result, Class<T> dto){
        this.listDTO = result.getContent().stream().map(entity -> modelMapper.map(entity, dto)).collect(Collectors.toList());
    }
}
