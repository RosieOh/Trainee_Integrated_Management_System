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

    @Query("select m from Member m where m.login_id = :id")
    Optional<Member> id_read(@Param("id") String id);

    @Query("select m from Member m where m.login_id = :id")
    Member id_read2(@Param("id") String id);

    @Query("select m from Member m where m.name = :name")
    Member getName(@Param("name") String name);

    @Query("select m from Member m where m.login_id = :id")
    Member findId(@Param("id") String id);

    Integer countByEmail(@Param("id") String id);

    @Query("select m from Member m where m.login_id = :id")
    Optional<Member> getMember(@Param("id") String id);


    @Query("select l from Member l where l.course.no = :cno")
    List<Member> voList2(Integer cno);

}