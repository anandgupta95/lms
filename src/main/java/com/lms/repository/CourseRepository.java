package com.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lms.model.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {
    @Query(value = "select * from  courses where subject = :subject", nativeQuery = true)
    List<Course> findCourseBySubject(@Param("subject") String subject);

    @Query(value = "select * from  courses where batch = :batch", nativeQuery = true)
    List<Course> findCourseByBatch(@Param("batch") String batch);

    @Query(value = "select * from courses where teacher_id = :teacherId" , nativeQuery = true)
    List<Course> findCoursesByTeacherId(@Param("teacherId") Long teacherId);
//    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Enrolement> enrolementList = new ArrayList<>();
}
