package com.lms.domain.Course.service;

import com.lms.domain.Course.dto.CourseDTO;
import com.lms.domain.Course.entity.Course;
import com.lms.domain.Course.repository.CourseRepository;
import com.lms.global.cosntant.Subject;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final ModelMapper modelMapper;
    private final CourseRepository courseRepository;

    @Override
    public List<CourseDTO> course_list() {
        List<Course> courseList = courseRepository.findAll();
        List<CourseDTO> courseDTOList = courseList.stream().map(
                        course -> modelMapper.map(course,CourseDTO.class))
                .collect(Collectors.toList());
        return courseDTOList;
    }

    @Override
    public List<CourseDTO> course_join_list(Subject subject) {
        List<Course> courseList = courseRepository.course_join_list(subject);
        List<CourseDTO> courseDTOList = courseList.stream().map(
                        course -> modelMapper.map(course,CourseDTO.class))
                .collect(Collectors.toList());
        return courseDTOList;
    }

    @Override
    public CourseDTO course_read(Integer no) {
        Optional<Course> course = courseRepository.findById(no);
        CourseDTO courseDTO = modelMapper.map(course, CourseDTO.class);
        return courseDTO;
    }

    @Override
    public void course_add(CourseDTO courseDTO) {
        Course course = modelMapper.map(courseDTO, Course.class);
        courseRepository.save(course);
    }

    @Override
    public void course_edit(CourseDTO courseDTO) {
        Optional<Course> course = courseRepository.findById(courseDTO.getNo());
        Course course1 = course.orElseThrow();
        courseRepository.save(course1);
    }

    @Override
    public List<CourseDTO> course_subject_list(Subject subject) {
        List<Course> courseList = courseRepository.course_subject_list(subject);
        List<CourseDTO> courseDTOList = courseList.stream().map(
                        course -> modelMapper.map(course,CourseDTO.class))
                .collect(Collectors.toList());
        return courseDTOList;
    }

    @Override
    public void delete_type(CourseDTO courseDTO) {
        Optional<Course> course = courseRepository.findById(courseDTO.getNo());
        Course course2 = course.orElseThrow();
        course2.setDelete_type(courseDTO.getDelete_type());
        courseRepository.save(course2);
    }
}
