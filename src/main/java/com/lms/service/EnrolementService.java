package com.lms.service;

import com.lms.annotation.RequiredRole;
import com.lms.model.Course;
import com.lms.model.EduPoint;
import com.lms.model.Enrolement;
import com.lms.model.Student;
import com.lms.repository.AuthRepository;
import com.lms.repository.CourseRepository;
import com.lms.repository.EnrolementRepository;
import com.lms.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EnrolementService {

    private final AuthRepository authRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final EnrolementRepository enrolementRepository;
    private final EduPointService eduPointService;

    public EnrolementService(AuthRepository authRepository,
                             CourseRepository courseRepository,
                             StudentRepository studentRepository,
                             EnrolementRepository enrolementRepository,
                             EduPointService eduPointService) {
        this.authRepository = authRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.enrolementRepository = enrolementRepository;
        this.eduPointService = eduPointService;
    }

    @RequiredRole({"STUDENT"})
    @Transactional
    public String createEnrolement(Long userId, Long courseId) {
        var auth = authRepository.findById(userId).orElseThrow();
        Student student = auth.getStudent();
        Course course = courseRepository.findById(courseId).orElseThrow();

        boolean alreadyEnrolled = enrolementRepository.existsByCourseIdAndStudentId(course.getId(), student.getId());
        if (alreadyEnrolled) {
            throw new RuntimeException("User is already enrolled in this course");
        }

        if (course.isPaid()) {
            EduPoint eduPoint = eduPointService.getEduPoint(student.getEduPoint().getId());

            if (eduPoint.getEduPoints() < course.getPrice()) {
                throw new RuntimeException("Not enough EduPoints to enroll in this paid course.");
            }

            eduPoint.setEduPoints(eduPoint.getEduPoints() - course.getPrice());
            eduPointService.updateEduPoint(eduPoint.getId(), eduPoint);
        }

        Enrolement enrolement = new Enrolement();
        enrolement.setCourse(course);
        enrolement.setStudent(student);
        if (course.isPaid()) {
            enrolement.setExpiryDate(LocalDateTime.now().plusDays(90)); // 3 months
        }

        enrolementRepository.save(enrolement);
        return course.isPaid() ? "Paid enrolment successful!" : "Enrolment successful!";
    }

    @RequiredRole({"ADMIN"})
    public List<Enrolement> getAllEnrolement() {
        return enrolementRepository.findAll();
    }

    // For teacher and student use
    public List<Enrolement> getEnrolementByUser(Long userId) {
        var auth = authRepository.findById(userId).orElseThrow();
        String role = auth.getRole().name();

        if (role.equals("STUDENT")) {
            return enrolementRepository.findByStudentId(auth.getStudent().getId());
        }

        if (role.equals("TEACHER")) {
            List<Enrolement> allEnrollments = new ArrayList<>();
            for (Course course : auth.getTeacher().getCourseList()) {
                allEnrollments.addAll(enrolementRepository.findByCourseId(course.getId()));
            }
            return allEnrollments;
        }

        return new ArrayList<>();
    }
}
