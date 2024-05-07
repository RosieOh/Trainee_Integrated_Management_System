package com.lms.domain.fileStudent.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(nullable = false)
    private String originFileName;

    @Column(nullable = false)
    private String savePath;

    @Column(nullable = false)
    private String saveFileName;

    @Column(nullable = false)
    private Long memberId;

}