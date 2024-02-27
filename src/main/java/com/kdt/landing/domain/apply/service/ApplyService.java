package com.kdt.landing.domain.apply.service;

import com.kdt.landing.domain.apply.dto.ApplyDTO;
import com.kdt.landing.domain.apply.entity.Apply;

import java.util.List;

public interface ApplyService {

    public List<ApplyDTO> findAll() throws Exception;
    public ApplyDTO findById(Long no) throws Exception;
    public void register(ApplyDTO applyDTO) throws Exception;
    public void modify(ApplyDTO applyDTO) throws Exception;
    public boolean emailCheck(String email) throws Exception;


}
