package com.lms.domain.board.entity;

import com.lms.domain.file.entity.File;
import com.lms.global.cosntant.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    //공지사항의 타입 - NOTCE는 전체 공지사항
    private String boardType;

    //기수 - 0일 경우에는 전체 공지사항
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int flag;

    //강의에 따른 공지사항
    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long cno;      // 강의 분류

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files = new ArrayList<>();

    private String writer;

    //게시글 고정 여부
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean pinned;

    //비밀글 여부
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean privated;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleteType;

    public void create(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public void change(String title, String content, boolean pinned, boolean privated) {
        this.title = title;
        this.content = content;
        this.pinned = pinned;
        this.privated = privated;
    }

    @Builder
    public Board(Long id, String title, String content, String writer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}

