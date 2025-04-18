package com.lms.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "auth_id", nullable = false, unique = true)
    private Auth auth;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courseList = new ArrayList<>();

    // Additional fields you should consider:
    @Column(
//            nullable = false,
            length = 100)
    private String fullName;

    @Column(length = 15)
    private String phoneNumber;

    @Column(length = 200)
    private String address;

    @Column(length = 100)
    private String department;

    @Column(length = 50)
    private String designation;

}
