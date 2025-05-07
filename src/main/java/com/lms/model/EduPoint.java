package com.lms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "edupoints")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EduPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    @JsonBackReference
    @JoinColumn(name = "student_id", nullable = false, unique = true)
    private Student student;

    @Column(nullable = false)
    private int eduPoints =100000;

    @Column(nullable = false)
    private LocalDateTime createdAt=LocalDateTime.now();

    private LocalDateTime updatedAt;

}
