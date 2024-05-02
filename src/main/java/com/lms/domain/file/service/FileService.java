package com.lms.domain.file.service;

import com.lms.domain.board.dto.BoardDTO;
import com.lms.domain.board.entity.Board;
import com.lms.domain.board.repository.BoardRepository;
import com.lms.domain.file.dto.FileDTO;
import com.lms.domain.file.entity.File;
import com.lms.domain.file.repository.FileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;


    public Long saveFile(FileDTO fileDTO) {
        Board board = boardRepository.findById(fileDTO.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("Board with id " + fileDTO.getBoardId() + " not found"));
        File file = new File(fileDTO.getOriginFileName(), fileDTO.getFileName(), fileDTO.getFilePath(), board);
        return fileRepository.save(file).getId();
    }

    public List<Long> saveFiles(List<FileDTO> fileDTOs) {
        List<File> files = fileDTOs.stream()
                .map(fileDTO -> {
                    Board board = boardRepository.findById(fileDTO.getBoardId())
                            .orElseThrow(() -> new IllegalArgumentException("Board with id " + fileDTO.getBoardId() + " not found"));
                    return new File(fileDTO.getOriginFileName(), fileDTO.getFileName(), fileDTO.getFilePath(), board);
                })
                .collect(Collectors.toList());
        List<File> savedFiles = fileRepository.saveAll(files);
        return savedFiles.stream().map(File::getId).collect(Collectors.toList());
    }

    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }

    public void deleteFilesByBoardId(Long boardId) {
        List<File> filesToDelete = fileRepository.findByBoardId(boardId);
        fileRepository.deleteAll(filesToDelete);
    }

    public List<FileDTO> findByBoardId(Long id) {
        List<File> lst = fileRepository.findByBoardId(id);
        if (lst != null && !lst.isEmpty()) {
            List<FileDTO> FileList = lst.stream().map(file -> modelMapper.map(file, FileDTO.class))
                    .collect(Collectors.toList());
            return FileList;
        } else {
            return Collections.emptyList();
        }
    }

    @Transactional
    public List<Long> getFileIdsByBoardId(Long boardId) {
        List<Long> fileIds = fileRepository.findIdsByBoardId(boardId);
        return fileIds;
    }

    @Transactional
    public FileDTO getFile(Long id) {
        File file = fileRepository.findById(id).get();
        FileDTO fileDTO = FileDTO.builder()
                .id(id)
                .originFileName(file.getOriginFileName())
                .fileName(file.getFileName())
                .filePath(file.getFilePath())
                .build();
        return fileDTO;
    }


}