package com.lms.service;

import com.lms.model.Student;
import com.lms.model.Auth;
import com.lms.repository.StudentRepository;
import com.lms.repository.AuthRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    AuthRepository AuthRepository;
    StudentRepository studentRepository;

    public StudentService(AuthRepository AuthRepository,StudentRepository studentRepository){
        this.AuthRepository = AuthRepository;
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Long AuthId, Student student){
        Auth authStudent = AuthRepository.findById(AuthId).orElseThrow(() -> new EntityNotFoundException("No Auth Found using this "+AuthId));

        if(!"STUDENT".equals(authStudent.getRole().name())){
            throw new IllegalArgumentException("Permission denied for creating student using this id : "+AuthId);
        }
        else{
            return studentRepository.save(student);
        }
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student getStudent(Long studentId){
        return studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("No Student Found with this id : "+studentId));
    }

    public Student updateStudent(Long studentId, Student studentDetails){
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("No Student Found using this id : "+studentId));
//        student.setName(studentDetails.getName());
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id){
        studentRepository.deleteById(id);
    }

}
