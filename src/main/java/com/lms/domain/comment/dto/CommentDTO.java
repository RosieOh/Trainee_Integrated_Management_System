package com.lms.domain.comment.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;

    private Long bid;

    @NotEmpty
    @Size(max = 2000)
    private String content;

    @NotEmpty
    private String writer;

    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;

    private boolean deleteType; //삭제 여부
}
