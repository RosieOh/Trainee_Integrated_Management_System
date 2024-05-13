package com.lms.domain.fileStudent.service;

import com.lms.domain.fileStudent.dto.FileStudentDTO;
import com.lms.domain.fileStudent.entity.FileStudent;
import com.lms.domain.fileStudent.repository.FileStudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class FileStudentService {

    private final FileStudentRepository fileStudentRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Long saveFile(FileStudentDTO fileStudentDTO) {
        FileStudent file = modelMapper.map(fileStudentDTO, FileStudent.class);
        fileStudentRepository.save(file);
        Optional<FileStudent> fileStudent = fileStudentRepository.returnFile(fileStudentDTO.getMemberId(), fileStudentDTO.getSaveFileName());
        Long no = fileStudent.get().getNo();
        return no;
    }

    @Transactional
    public FileStudentDTO getFile(Long memberId, Long no) {
        Optional<FileStudent> fileStudent = fileStudentRepository.getFile(memberId, no);
        FileStudentDTO fileStudentDTO = modelMapper.map(fileStudent, FileStudentDTO.class);
        return fileStudentDTO;
    }

    @Transactional
    public FileStudentDTO fileDelete(Long memberId, Long no) {
        Optional<FileStudent> fileStudent = fileStudentRepository.getFile(memberId, no);
        FileStudentDTO fileStudentDTO = modelMapper.map(fileStudent, FileStudentDTO.class);
        return fileStudentDTO;
    }
}