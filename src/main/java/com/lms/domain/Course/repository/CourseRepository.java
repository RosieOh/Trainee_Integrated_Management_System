package com.lms.domain.Course.repository;
import com.lms.domain.Course.entity.Course;
import com.lms.global.cosntant.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    //진행 중인 강의 가져오기
    @Query("select c from Course c where c.subject= :subject and c.delete_type = 'ing' order by c.flag asc")
    List<Course> ingSubject(Subject subject);

    //기수 중복 체크
    @Query("select count (c) from Course c where c.flag = :flag and c.subject = :subject")
    int countBySubjectFlag(@Param("flag") int flag, @Param("subject") Subject subject);
}