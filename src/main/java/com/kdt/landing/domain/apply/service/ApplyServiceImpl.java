package com.kdt.landing.domain.apply.service;

import com.kdt.landing.domain.apply.dto.ApplyDTO;
import com.kdt.landing.domain.apply.entity.Apply;
import com.kdt.landing.domain.apply.repository.ApplyRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ApplyServiceImpl implements ApplyService{

    @Autowired
    private ApplyRepository applyRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<ApplyDTO> findAll() {
        List<Apply> applyList = applyRepository.findAll();
        List<ApplyDTO> applyDTOList = applyList.stream().map(
                        user -> modelMapper.map(user, ApplyDTO.class))
                .collect(Collectors.toList());
        return applyDTOList;
    }

    @Override
    public ApplyDTO findById(Long no) {
        Optional<Apply> apply = applyRepository.findById(no);
        ApplyDTO applyDTO = modelMapper.map(apply, ApplyDTO.class);
        return applyDTO;
    }

    @Override
    public void register(ApplyDTO applyDTO) {
        String emailEn = passwordEncoder.encode(applyDTO.getEmail());
        String telEn = passwordEncoder.encode(applyDTO.getTel());
        applyDTO.setEmail(emailEn);
        applyDTO.setTel(telEn);
        Apply apply = modelMapper.map(applyDTO, Apply.class);
        applyRepository.save(apply);
    }

    @Override
    public void modify(ApplyDTO applyDTO) {
        Apply apply = modelMapper.map(applyDTO, Apply.class);
        applyRepository.save(apply);


    }

    @Override
    public boolean emailCheck(String email) {
        boolean pass = true;
//        int cnt = applyRepository.countBy(email);
//        if(cnt > 0) pass = false;
        return pass;
    }
}
