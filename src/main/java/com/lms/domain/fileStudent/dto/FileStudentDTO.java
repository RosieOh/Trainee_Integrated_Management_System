package com.lms.domain.fileStudent.dto;

import lombok.*;

@Getter
@Setter
@ToString
public class FileStudentDTO {

    private Long no;
    private String originFileName;
    private String savePath;
    private String saveFileName;
    private Long memberId;

}