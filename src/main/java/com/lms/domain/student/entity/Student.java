package com.lms.domain.counsel.entity;

import com.lms.domain.student.entity.Student;
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
    private Long no;

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private String cert;

    @Column(nullable = true)
    private String exp;

    @Column(nullable = true)
    private String stack;

    @Column(nullable = true)
    private String OA;

    @Column(nullable = true)
    private String content;

    @Column(nullable = true)
    private String satis;

    @Column(nullable = true)
    private String edu_data;

    @Column(nullable = true)
    private String instructor;

    @Column(nullable = true)
    private String task;

    @Column(nullable = true)
    private String env;

    @Column(nullable = true)
    private String goal;

    @Column(nullable = true)
    private String doc;

    @Column(nullable = true)
    private String personal;

    @Column(nullable = true)
    private String opinion;

    @Column(nullable = true)
    private String etc;

    @Column(nullable = true)
    private String name;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "couno", referencedColumnName = "no")
    private Student student;   // 학생

    @Column(nullable = true)
    private LocalDateTime date;
}
