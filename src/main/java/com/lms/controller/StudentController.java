package com.lms.controller;

import com.lms.annotation.RequiredRole;
import com.lms.model.Student;
import com.lms.model.Auth;
import com.lms.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/student")
@RestController
public class StudentController {

    StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping("/{AuthId}")
    @RequiredRole(Auth.Role.STUDENT)
    public ResponseEntity<?> createStudent(@PathVariable Long AuthId, @RequestBody Student student){
        return ResponseEntity.ok(studentService.createStudent(AuthId,student));
    }

    @GetMapping
    @RequiredRole(Auth.Role.STUDENT)
    public ResponseEntity<?> getAllStudents(){
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    @RequiredRole(Auth.Role.STUDENT)
    public ResponseEntity<?> getStudent(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @PutMapping("/{id}")
    @RequiredRole(Auth.Role.STUDENT)
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student student){
        return ResponseEntity.ok(studentService.updateStudent(id,student));
    }

    @DeleteMapping("/{id}")
    @RequiredRole(Auth.Role.STUDENT)
    public void delete(@PathVariable Long id){
        studentService.deleteStudent(id);
    }
}
