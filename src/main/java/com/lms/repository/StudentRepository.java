package com.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lms.model.Student;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByAuthId(Long authId);
}
