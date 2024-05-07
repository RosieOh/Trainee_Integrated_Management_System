package com.lms.domain.fileStudent.dto;

import lombok.*;

@Getter
@Setter
@ToString
public class FileStudentDTO {

    private Long id;
    private String originFileName;
    private String filePath;
    private String fileName;
    private Long memberId;

}