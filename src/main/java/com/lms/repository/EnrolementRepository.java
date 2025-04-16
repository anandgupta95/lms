package com.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lms.model.Enrolement;

import java.util.List;

public interface EnrolementRepository extends JpaRepository<Enrolement,Long> {
    boolean existsByCourseIdAndStudentId(Long courseId, Long studentId);
    List<Enrolement> findByStudentId(Long studentId);
    List<Enrolement> findByCourseId(Long courseId);
}
