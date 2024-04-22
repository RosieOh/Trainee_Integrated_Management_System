package com.lms.domain.student.service;

import com.lms.domain.member.entity.Member;
import com.lms.domain.student.dto.StudentDTO;
import com.lms.domain.student.entity.Student;
import com.lms.domain.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;

    @Override
    public List<StudentDTO> student_list() {
        List<Student> studentList = studentRepository.findAll();
        List<StudentDTO> studentDTOList = studentList.stream().map(
                        student -> modelMapper.map(student,StudentDTO.class))
                .collect(Collectors.toList());
        return studentDTOList;
    }
    @Override
    public StudentDTO student_read(Long no) {
        Optional<Student> student = studentRepository.findById(no);
        StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
        return studentDTO;
    }

    @Override
    public void student_add(StudentDTO studentDTO) {
        Student student = modelMapper.map(studentDTO, Student.class);
        studentRepository.save(student);
    }

    @Override
    public void student_edit(StudentDTO studentDTO) {
        Student student = modelMapper.map(studentDTO, Student.class);
        studentRepository.save(student);
    }


}
