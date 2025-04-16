package com.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lms.model.Student;
public interface StudentRepository extends JpaRepository<Student,Long> {
}
