package com.kdt.landing.domain.user.repository;
import com.kdt.landing.domain.user.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from Member m where m.email = :email")
    Optional<Member> getWithRoles(String email);

    @EntityGraph(attributePaths = "roleSet")
    Member findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update Member m set m.pw =:pw where m.id = :id ")
    void updatePassword(@Param("pw") String password, @Param("id") Long id);

    @Query("select m from Member m where m.id =:id")
    Member findById(@Param("id") String id);


    @Query("select m from Member m where m.email =:email")
    Member existsMemberByEmail(String email);

    boolean existsByEmail(String email);


}