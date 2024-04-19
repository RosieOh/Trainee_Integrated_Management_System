package com.lms.domain.student.service;

import com.lms.domain.student.dto.CounselDTO;
import com.lms.domain.student.entity.Student;

import java.util.List;

public interface StudentService {

    public List<Student> student_list(Student student);
    public Student student_read(Long no);
    public void student_add(Student student);
    public void student_edit(Student student);
}
