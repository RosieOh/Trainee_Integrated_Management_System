//
//package com.lms.global.s3.service;
//
//import com.lms.global.s3.component.AmazonS3ResourceStorage;
//import com.lms.global.s3.dto.FileDetail;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//
//@Service
//@RequiredArgsConstructor
//public class FileUploadService {
//    private final AmazonS3ResourceStorage amazonS3ResourceStorage;
//
//    public FileDetail save(MultipartFile multipartFile) {
//        FileDetail fileDetail = FileDetail.multipartOf(multipartFile);
//        amazonS3ResourceStorage.store(fileDetail.getPath(), multipartFile);
//        return fileDetail;
//    }
//}
