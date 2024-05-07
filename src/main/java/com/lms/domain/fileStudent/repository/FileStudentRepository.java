package com.lms.domain.fileStudent.repository;

import com.lms.domain.fileStudent.entity.FileStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FileStudentRepository extends JpaRepository<FileStudent, Long> {

    @Query("SELECT f FROM FileStudent f WHERE f.memberId = :memberId")
    Optional<FileStudent> findMemberId(Long memberId);

    @Query("SELECT f FROM FileStudent f WHERE f.memberId = :memberId and f.no = :no")
    Optional<FileStudent> getFile(Long memberId, Long no);

    @Query("SELECT f FROM FileStudent f WHERE f.memberId = :memberId and f.saveFileName = :saveFileName")
    Optional<FileStudent> returnFile(Long memberId, String saveFileName);
}
