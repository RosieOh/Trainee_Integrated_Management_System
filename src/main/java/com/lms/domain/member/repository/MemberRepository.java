package com.lms.domain.member.repository;
import com.lms.domain.member.dto.MemberVO;
import com.lms.domain.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from Member m where m.email = :email")
    Optional<Member> getWithRoles(String email);

    @EntityGraph(attributePaths = "roleSet")
    Member findByEmail(String email);

    @Query("select m from Member m where m.email = :email")
    Optional<Member> findByEmail2(String email);

    boolean existsByEmail(String email);

    @Query("select m from Member m where m.email = :email")
    Member getEmail(@Param("email") String email);

    @Query("select m from Member m where m.name = :name")
    Member getName(@Param("name") String name);


    Integer countByEmail(@Param("email") String email);

    @Query("select m from Member m where m.email = :email")
    Optional<Member> getMember(@Param("email") String email);


    @Query("select l from Member l where l.course.no = :cno")
    List<Member> voList2(Integer cno);

}