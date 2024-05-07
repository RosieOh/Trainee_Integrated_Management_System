package com.lms.domain.member.dto;

import com.lms.domain.course.dto.CourseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
    private MemberDTO member;
    private CourseDTO course;
    private Integer cno;
}
