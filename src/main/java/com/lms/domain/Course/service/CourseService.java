package com.lms.domain.Course.service;

import com.lms.domain.Course.dto.CourseDTO;
import com.lms.domain.Course.entity.Course;

import java.util.List;

public interface CourseService {

    List<CourseDTO> course_List();
    public CourseDTO course_One(Integer no);
    public void course_Add(CourseDTO courseDTO);
    public void course_Edit(CourseDTO courseDTO);


}