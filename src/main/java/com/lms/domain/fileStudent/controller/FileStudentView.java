package com.lms.domain.fileStudent.controller;

import com.lms.domain.fileStudent.dto.FileStudentDTO;
import com.lms.domain.fileStudent.service.FileStudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@Log4j2
@RequiredArgsConstructor
public class FileStudentView {

    private final FileStudentService fileStudentService;

    @ResponseBody
    @GetMapping("/student/down/{memberId}/{no}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("memberId") Long memberId, @PathVariable("no") Long no) throws Exception {
        // 파일 서비스를 통해 fileId로 파일 정보를 가져옴
        FileStudentDTO fileDTO = fileStudentService.getFile(memberId, no);

        log.info("fileDTO ---------" + fileDTO);
        // 파일 경로 생성
        Path path = Paths.get(fileDTO.getSavePath());
        log.info("path ---------" + path);

        // 파일 스트림에서 Resource를 생성
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        log.info("resource ---------" + resource);

        // 응답 헤더를 설정
        HttpHeaders headers = new HttpHeaders();
        log.info("headers ---------" + headers);

        try {
            // 파일 이름을 UTF-8로 인코딩
            String encodingFileName = new String(fileDTO.getOriginFileName().getBytes("UTF-8"), "ISO-8859-1");
            log.info("encodingFileName ---------" + encodingFileName);

            // Content-Disposition 헤더에 인코딩된 파일 이름을 설정.
            headers.setContentDispositionFormData("attachment", encodingFileName);

            // 컨텐츠 타입을 이전 데이터로 설정
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        } catch (IOException e) {
            log.error("파일 이름 인코딩 중 오류 발생 : {}", e.getMessage());
        }

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
