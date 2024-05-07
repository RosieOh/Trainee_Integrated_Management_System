package com.lms.domain.Course.service;

import com.lms.domain.Course.dto.CourseDTO;
import com.lms.domain.Course.entity.Course;
import com.lms.global.cosntant.Subject;

import java.util.List;

public interface CourseService {

    public List<CourseDTO> course_list();
    public List<CourseDTO> course_join_list(Subject subject);
    public CourseDTO course_read(Integer no);
    public void course_add(CourseDTO courseDTO);
    public void course_edit(CourseDTO courseDTO);
    public List<CourseDTO> course_subject_list(Subject subject);
    public void delete_type(CourseDTO courseDTO);
    public List<CourseDTO> ingSubject(Subject subject);
    //기수 체크
    public boolean flag_check(CourseDTO courseDTO);
}