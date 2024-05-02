package com.lms.domain.file.repository;

import com.lms.domain.board.entity.Board;
import com.lms.domain.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    @Query("SELECT f FROM File f JOIN f.board b WHERE b.id = :id")
    List<File> findByBoardId(Long id);

    @Query(value = "SELECT id FROM file WHERE board_id = :boardId", nativeQuery = true)
    List<Long> findIdsByBoardId(Long boardId);
}
