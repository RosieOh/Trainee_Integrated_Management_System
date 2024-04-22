package com.lms.domain.student.service;

import com.lms.domain.student.dto.StudentDTO;

import java.util.List;

public interface StudentService {

    public List<StudentDTO> student_list();
    public StudentDTO student_read(Long no);
    public void student_add(StudentDTO studentDTO);
    public void student_edit(StudentDTO studentDTO);
}
