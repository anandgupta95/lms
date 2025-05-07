package com.lms.repository;

import com.lms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lms.model.Teacher;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher,Long>{
    Optional<Teacher> findByAuthId(Long authId);
}
