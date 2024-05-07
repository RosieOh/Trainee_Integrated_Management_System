//package com.lms.global.s3.service;
//
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.ListObjectsV2Request;
//import com.amazonaws.services.s3.model.ListObjectsV2Result;
//import com.amazonaws.services.s3.model.S3ObjectSummary;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class S3Service {
//
//    private final AmazonS3Client amazonS3Client;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucketName;
//
//    @Value("${cloud.aws.region.static}")
//    private String region;
//
//    public String getThumbnailPath(String path) {
//        return amazonS3Client.getUrl(bucketName, path).toString();
//    }
//
//    // S3 폴더 내 파일 리스트 전달
//    public List<String> getFileList(String directory) {
//        List<String> fileList = new ArrayList<>();
//        ListObjectsV2Request imagesRequest = new ListObjectsV2Request()
//                .withBucketName(bucketName)
//                .withPrefix(directory + "/images");
//        ListObjectsV2Result imagesResult = amazonS3Client.listObjectsV2(imagesRequest);
//        List<S3ObjectSummary> objectSummaries = imagesResult.getObjectSummaries();
//
//        for (S3ObjectSummary objectSummary : objectSummaries) {
//            String key = objectSummary.getKey();
//            if (!key.equals(directory + "/")) {
//                fileList.add("https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key);
//            }
//        }
//
//        ListObjectsV2Request portfolioRequest = new ListObjectsV2Request()
//                .withBucketName(bucketName)
//                .withPrefix(directory + "/portfolio/");
//        ListObjectsV2Result portfolioResult = amazonS3Client.listObjectsV2(portfolioRequest);
//        List<S3ObjectSummary> portfolioSummaries = portfolioResult.getObjectSummaries();
//        for (S3ObjectSummary objectSummary : portfolioSummaries) {
//            String key = objectSummary.getKey();
//            if (!key.equals(directory + "/portfolio/")) {
//                fileList.add("https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key);
//            }
//        }
//
//        return fileList;
//    }
//}