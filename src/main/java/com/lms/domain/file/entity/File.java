package com.lms.domain.file.entity;


import com.lms.domain.board.entity.Board;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originFileName;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public File(Long id, String originFileName, String fileName, String filePath, Board board) {
        this.id = id;
        this.originFileName = originFileName;
        this.filePath = filePath;
        this.fileName = fileName;
        this.board = board;
    }

    public File(String originFileName, String fileName, String filePath, Board board) {
        this.originFileName = originFileName;
        this.filePath = filePath;
        this.fileName = fileName;
        this.board = board;
    }

    public void change(Long id, String fileName, String filePath, String originFileName) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.originFileName = originFileName;
    }


}