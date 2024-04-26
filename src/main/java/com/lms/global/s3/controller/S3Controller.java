package com.lms.global.s3.controller;

import com.lms.global.s3.dto.FileDetail;
import com.lms.global.s3.service.FileDownloadService;
import com.lms.global.s3.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
@RestController
@RequestMapping(value = "/upload", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class S3Controller {

    private final FileUploadService fileUploadService;
    private final FileDownloadService fileDownloadService;

    @PostMapping
    public ResponseEntity<FileDetail> post(@RequestPart(value = "file", required = false) MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            // 파일이 첨부되지 않았을 때
            log.info("파일이 첨부되지 않았습니다.");
            return ResponseEntity.badRequest().body(null);
        } else {
            // 파일이 첨부되었을 때
            log.info("파일이 성공적으로 첨부되었습니다.");
            return ResponseEntity.ok(fileUploadService.save(multipartFile));
        }
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam String fileName) {
        try {
            return fileDownloadService.getObject(fileName);
        } catch (Exception e) {
            log.error("Error while downloading file: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}