package com.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lms.model.Teacher;
public interface TeacherRepository extends JpaRepository<Teacher,Long>{
}
