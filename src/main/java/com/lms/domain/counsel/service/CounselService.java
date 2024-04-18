package com.lms.domain.counsel.service;

import com.lms.domain.board.dto.BoardDTO;
import com.lms.domain.board.entity.Board;
import com.lms.domain.counsel.dto.CounselDTO;
import com.lms.domain.counsel.entity.Counsel;

import java.util.List;

public interface CounselService {

    public List<CounselDTO> counsel_List(CounselDTO counselDTO);
    public void counsel_Add(CounselDTO counselDTO);
    public void counsel_Edit(CounselDTO counselDTO);
    public CounselDTO counsel_Read(Long no);



}
