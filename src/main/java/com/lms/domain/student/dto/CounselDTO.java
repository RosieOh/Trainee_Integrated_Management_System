package com.lms.domain.counsel.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CounselDTO {

    private Long no;

    @NotEmpty
    @Size(max = 500)
    private String address;

    @NotEmpty
    @Size(max = 500)
    private String cert;

    @NotEmpty
    @Size(max = 500)
    private String exp;

    @NotEmpty
    @Size(max = 500)
    private String stack;

    @NotEmpty
    @Size(max = 500)
    private String OA;

    @NotEmpty
    @Size(max = 10000)
    private String content;

    @NotEmpty
    @Size(max = 10000)
    private String satis;

    @NotEmpty
    @Size(max = 500)
    private String edu_data;

    @NotEmpty
    @Size(max = 500)
    private String instructor;

    @NotEmpty
    private String task;

    @NotEmpty
    private String env;

    @NotEmpty
    private String goal;

    @NotEmpty
    private String doc;

    @NotEmpty
    private String personal;

    @NotEmpty
    private String opinion;

    @NotEmpty
    private String etc;

    @NotEmpty
    @Size(max = 50)
    private String name;

    private LocalDateTime date;

}
