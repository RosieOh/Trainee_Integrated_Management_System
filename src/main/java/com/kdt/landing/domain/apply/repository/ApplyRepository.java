package com.kdt.landing.domain.apply.repository;

import com.kdt.landing.domain.apply.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyRepository extends JpaRepository<Apply, Long> {

}
