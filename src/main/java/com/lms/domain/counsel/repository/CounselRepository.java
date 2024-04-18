package com.lms.domain.counsel.repository;

import com.lms.domain.counsel.entity.Counsel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CounselRepository extends JpaRepository<Counsel, Long> {

}
