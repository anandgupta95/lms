package com.lms.service;

import com.lms.model.Enrolement;
import com.lms.repository.CourseRepository;
import com.lms.repository.EnrolementRepository;
import com.lms.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrolementService {

    CourseRepository courseRepository;
    StudentRepository studentRepository;
    EnrolementRepository enrolementRepository;

    public EnrolementService(CourseRepository courseRepository, StudentRepository studentRepository, EnrolementRepository enrolementRepository){
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.enrolementRepository = enrolementRepository;
    }
    //for student
    public Enrolement createEnrolement(Enrolement enrolement) {
        Long courseId = enrolement.getCourse().getId();
        Long studentId = enrolement.getStudent().getId();

        if (!enrolementRepository.existsByCourseIdAndStudentId(courseId, studentId)) {
            return enrolementRepository.save(enrolement);
        } else {
            throw new RuntimeException("User is already enrolled in this course");
        }
    }
    //for admin use
    public List<Enrolement> getAllEnrolement(){
        return enrolementRepository.findAll();
    }
    //for teacher as well as student use
    public List<Enrolement> getEnrolementByStudent(Long studentId)
    {
        return enrolementRepository.findByStudentId(studentId);
    }
    // for teacher user only
    public List<Enrolement> getEnrolementByCourseId(Long courseId){
        return enrolementRepository.findByCourseId(courseId);
    }
    //delete enrolement can by student, teacher and admin
    public void deleteEnrolement(Long enrolementId){
        enrolementRepository.deleteById(enrolementId);
    }
}
