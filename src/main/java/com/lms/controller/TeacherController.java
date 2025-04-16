package com.lms.controller;

import com.lms.annotation.RequiredRole;
import com.lms.model.Teacher;
import com.lms.model.Auth;
import com.lms.service.TeacherService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/teacher")
@RestController
public class TeacherController {
    TeacherService teacherService;

    public TeacherController(TeacherService teacherService){
        this.teacherService = teacherService;
    }

    @PostMapping("/{id}")
    @RequiredRole(Auth.Role.TEACHER)
    public ResponseEntity<?> createTeacher(@PathVariable Long id, @RequestBody Teacher teacher) throws BadRequestException {
        return ResponseEntity.ok(teacherService.createTeacher(id,teacher));
    }

    @GetMapping
    @RequiredRole(Auth.Role.TEACHER)
    public ResponseEntity<?> getTeacher(){
        return ResponseEntity.ok(teacherService.getTeacher());
    }

    @GetMapping("/{id}")
    @RequiredRole(Auth.Role.TEACHER)
    public ResponseEntity<?> getTeacher(@PathVariable Long id){
        return  ResponseEntity.ok(teacherService.getTeacher(id));
    }

    @PutMapping("/{id}")
    @RequiredRole(Auth.Role.TEACHER)
    public ResponseEntity<?> updateTeacher(@PathVariable Long id, @RequestBody Teacher teacher)
    {
        return ResponseEntity.ok(teacherService.updateTeacher(id,teacher));
    }

    @DeleteMapping("/{id}")
    @RequiredRole(Auth.Role.TEACHER)
    public void delete(@PathVariable Long id){
        teacherService.deleteTeacher(id);
    }
}
