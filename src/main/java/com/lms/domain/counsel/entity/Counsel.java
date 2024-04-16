package com.lms.domain.counsel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Counsel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "counsel_no")
    private Long no;

    @Column(length = 500, nullable = false)
    private String address;

    @Column(length = 500, nullable = false)
    private String cert;

    @Column(length = 500, nullable = false)
    private String exp;

    @Column(length = 500, nullable = false)
    private String stack;

    @Column(length = 500, nullable = false)
    private String OA;

    @Column(length = 500, nullable = false)
    private String content;

    @Column(length = 500, nullable = false)
    private String satis;

    @Column(length = 500, nullable = false)
    private String edu_data;

    @Column(length = 500, nullable = false)
    private String instructor;

    @Column(length = 500, nullable = false)
    private String task;

    @Column(length = 500, nullable = false)
    private String env;

    @Column(length = 500, nullable = false)
    private String goal;

    @Column(length = 500, nullable = false)
    private String doc;

    @Column(length = 500, nullable = false)
    private String personal;

    @Column(length = 500, nullable = false)
    private String opinion;

    @Column(length = 500, nullable = false)
    private String etc;

    @Column(length = 500, nullable = false)
    private String name;

    @Column(length = 500, nullable = false)
    private String student;

    @Column(length = 500, nullable = false)
    private LocalDateTime date;
}
