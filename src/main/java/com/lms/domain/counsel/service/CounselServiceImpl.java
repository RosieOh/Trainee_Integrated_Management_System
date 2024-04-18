package com.lms.domain.counsel.service;

import com.lms.domain.counsel.dto.CounselDTO;
import com.lms.domain.counsel.entity.Counsel;
import com.lms.domain.counsel.repository.CounselRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CounselServiceImpl implements CounselService {

    private final ModelMapper modelMapper;
    private final CounselRepository counselRepository;

    @Override
    public List<CounselDTO> counsel_List(CounselDTO counselDTO) {
        List<Counsel> counselList = counselRepository.findAll();
        List<CounselDTO> counselDTOList = counselList.stream().map(
                         counsel -> modelMapper.map(counsel, CounselDTO.class))
                .collect(Collectors.toList());
        return counselDTOList;
    }

    @Override
    public void counsel_Add(CounselDTO counselDTO) {
        Counsel counsel = modelMapper.map(counselDTO, Counsel.class);
        counselRepository.save(counsel);
    }

    @Override
    public void counsel_Edit(CounselDTO counselDTO) {
        Optional<Counsel> counsel = counselRepository.findById(counselDTO.getNo());
        Counsel counsel1 = counsel.orElseThrow();
        counselRepository.save(counsel1);
    }

    @Override
    public CounselDTO counsel_Read(Long no) {
        Optional<Counsel> result = counselRepository.findById(no);
        Counsel counsel = result.orElseThrow();
        CounselDTO counselDTO = modelMapper.map(counsel, CounselDTO.class);
        return counselDTO;
    }
}
