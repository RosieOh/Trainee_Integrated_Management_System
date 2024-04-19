//package com.lms.domain.student.service;
//
//import com.lms.domain.student.entity.Student;
//import com.lms.domain.student.repository.StudentRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@Log4j2
//@RequiredArgsConstructor
//@Transactional
//public class StudentServiceImpl implements StudentService {
//
//    private final ModelMapper modelMapper;
//    private final StudentRepository studentRepository;
//
//    @Override
//    public List<Student> student_list(Student student) {
//        List<Student> studentList = studentRepository.findAll();
////        List<CounselDTO> counselDTOList = studentList.stream().map(
////                        student -> modelMapper.map(student, CounselDTO.class))
////                .collect(Collectors.toList());
//        return studentList;
//    }
//    @Override
//    public Student student_read(Long no) {
//        Student student = studentRepository.findById(no);
////        Optional<Student> student = studentRepository.findById(no);
////        CounselDTO counselDTO = modelMapper.map(student, CounselDTO.class);
//        return student;
//    }
//
//    @Override
//    public void student_add(Student student) {
////        Student student = modelMapper.map(counselDTO, Student.class);
//        studentRepository.save(student);
//    }
//
//    @Override
//    public void student_edit(Student student) {
////        Optional<Student> student = studentRepository.findById(counselDTO.getNo());
////        Student student1 = student.orElseThrow();
//        studentRepository.save(student);
//    }
//
//
//}
