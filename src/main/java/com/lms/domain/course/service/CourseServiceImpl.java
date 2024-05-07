package com.lms.domain.course.service;

import com.lms.domain.course.dto.CourseDTO;
import com.lms.domain.course.entity.Course;
import com.lms.domain.course.repository.CourseRepository;
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
        List<Course> courseList = courseRepository.admin_subject_list();
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
        Course course = modelMapper.map(courseDTO, Course.class);
        courseRepository.save(course);
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

    @Override
    public List<CourseDTO> ingSubject(Subject subject) {
        List<Course> courseList = courseRepository.ingSubject(subject);
        List<CourseDTO> courseDTOList = courseList.stream().map(
                        course -> modelMapper.map(course,CourseDTO.class))
                .collect(Collectors.toList());
        return courseDTOList;
    }

    //기수 체크
    @Override
    public boolean flag_check(CourseDTO courseDTO) {

        // CourseDTO에서 subject와 flag 정보를 가져와서 Course 객체 생성
        Course course = new Course();
        course.setSubject(courseDTO.getSubject());
        course.setFlag(courseDTO.getFlag());

        log.info(course.getSubject() );
        log.info(course.getFlag());
        boolean pass = true;
        int cnt = courseRepository.countBySubjectFlag(course.getFlag(), course.getSubject());
        log.info(cnt +"cnt");
        if (cnt > 0) pass = false;
        log.info(pass +"pass");
        return pass;
    }
}
