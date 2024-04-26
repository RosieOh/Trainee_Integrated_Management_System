package com.lms.domain.member.repository;
import com.lms.domain.member.entity.Member;
import com.lms.global.cosntant.Role;
import com.lms.global.cosntant.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.id = :id")
    Optional<Member> id_read(@Param("id") String id);

    @Query("select m from Member m where m.id = :id")
    Member findId(@Param("id") String id);

    @Query("select count(m) from Member m where m.id = :id")
    Integer countId(@Param("id") String id);

    @Query("select l from Member l where l.course.no = :cno")
    List<Member> voList2(Integer cno);

    @Query("select m from Member m where m.no > 1")
    List<Member> member_list();


    //검색 및 페이징 처리
    @Query("SELECT m FROM Member m " +
            "JOIN Course c ON c.no = m.course.no " +
            "WHERE m.name LIKE %:keyword% " +
            "AND c.flag = :flag " +
            "AND c.subject = :subject " +
            "AND m.role = :role")
    Page<Member> findByKeywordAndFlagAndSubjectAndRole(String keyword, Integer flag, Subject subject, Role role, Pageable pageable);


}