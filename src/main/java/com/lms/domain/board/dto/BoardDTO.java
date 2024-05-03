package com.lms.domain.board.dto;

import com.lms.domain.Course.dto.CourseDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long id;

    @NotEmpty
    @Size(max = 500)
    private String title;

    @NotEmpty
    @Size(max = 2000)
    private String content;

    @NotEmpty
    @Size(max = 50)
    private String boardType;

    private int flag;               //기수

    //강의에 따른 공지사항
    private Long cno;      // 강의 분류

    @NotEmpty
    @Size(max = 50)
    private String writer;

    private List<Long> fileIds;

    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;

    private boolean pinned; //게시글 고정 여부

    private boolean privated; //비밀글 여부

    private boolean deleteType; //삭제 여부

}
