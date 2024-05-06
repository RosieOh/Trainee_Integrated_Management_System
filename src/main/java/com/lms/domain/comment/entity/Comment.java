package com.lms.domain.comment.entity;

import com.lms.global.cosntant.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long bid; //부모 게시글의 번호

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(nullable = false)
    private String writer;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleteType;

    public void delete(boolean deleteType) {
        this.deleteType = deleteType;
    }
}
