package com.lms.domain.Course.repository;
import com.lms.domain.Course.entity.Course;
import com.lms.global.cosntant.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query("select c from Course c where c.no > 1 order by c.no asc")
    List<Course> admin_subject_list();

    @Query("select c from Course c where c.subject = :subject and c.no > 1 order by c.flag asc")
    List<Course> course_subject_list(Subject subject);

    @Query("select c from Course c where c.subject= :subject and c.delete_type = 'y' order by c.flag asc")
    List<Course> course_join_list(Subject subject);

    @Query("select c from Course c where c.subject= :subject")
    Integer course_one(Subject subject);
}