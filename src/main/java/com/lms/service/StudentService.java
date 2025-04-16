package com.lms.service;

import com.lms.model.Student;
import com.lms.model.User;
import com.lms.repository.StudentRepository;
import com.lms.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    UserRepository userRepository;
    StudentRepository studentRepository;

    public StudentService(UserRepository userRepository,StudentRepository studentRepository){
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Long userId, Student student){
        User userStudent = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("No User Found using this "+userId));

        if(!"STUDENT".equals(userStudent.getRole().name())){
            throw new IllegalArgumentException("Permission denied for creating student using this id : "+userId);
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
