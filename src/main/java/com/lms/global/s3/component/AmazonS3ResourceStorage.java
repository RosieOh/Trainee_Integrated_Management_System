//package com.lms.global.s3.component;
//
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.AmazonS3Exception;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.DeleteObjectRequest;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.lms.global.util.MultipartUtil;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//
//@Component
//@Log4j2
//@ConditionalOnProperty(prefix = "cloud.aws.s3", name = "bucket")
//@RequiredArgsConstructor
//public class AmazonS3ResourceStorage {
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//    private final AmazonS3Client amazonS3Client;
//
//    public void store(String fullPath, MultipartFile multipartFile) {
//        File file = new File(MultipartUtil.getLocalHomeDirectory(), fullPath);
//        try {
//            multipartFile.transferTo(file);
//            amazonS3Client.putObject(new PutObjectRequest(bucket, fullPath, file)
//                    .withCannedAcl(CannedAccessControlList.PublicRead));
//        } catch (IOException e) {
//            // 파일 전송 중 예외 처리
//            throw new RuntimeException("Failed to transfer multipart file to local file system", e);
//        } catch (AmazonS3Exception e) {
//            // Amazon S3 업로드 중 예외 처리
//            throw new RuntimeException("Failed to upload file to Amazon S3", e);
//        } finally {
//            // 파일 삭제
//            if (file.exists() && !file.delete()) {
//            }
//        }
//    }
//}