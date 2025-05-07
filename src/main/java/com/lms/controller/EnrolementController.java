package com.lms.controller;

import com.lms.annotation.RequiredRole;
import com.lms.model.Enrolement;
import com.lms.service.EnrolementService;
import com.lms.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/enrolements")
public class EnrolementController {
    private final EnrolementService enrolementService;
    private final JwtUtil jwtUtil;

    @Autowired
    public EnrolementController(EnrolementService enrolementService,JwtUtil jwtUtil) {
        this.enrolementService = enrolementService;
        this.jwtUtil = jwtUtil;
    }

    // Endpoint for students to create enrolement
    @RequiredRole({"STUDENT"})
    @PostMapping("/{courseId}")
    public String createEnrolement(@RequestHeader ("Authorization") String requestHeader,@PathVariable Long courseId) {
        Long studentId = jwtUtil.extractId(requestHeader.replace("Bearer ",""));
        return enrolementService.createEnrolement(studentId,courseId);
    }

    // Admin only: Get all enrolements
    @RequiredRole({"ADMIN"})
    @GetMapping
    public ResponseEntity<List<Enrolement>> getAllEnrolements() {
        return ResponseEntity.ok(enrolementService.getAllEnrolement());
    }

//    // Student & Teacher: Get enrolements by student ID
//    @GetMapping("/user")
//    public ResponseEntity<List<Enrolement>> getEnrolementsByStudentId(@RequestHeader ("Authorization") String requestHeader) {
//        Long id = jwtUtil.extractId(requestHeader.replace("Bearer ",""));
//        return ResponseEntity.ok(enrolementService.getEnrolementByStudent(id));
//    }


    // Delete enrolement - accessible by student/teacher/admin
//    @DeleteMapping("/{enrolementId}")
//    public ResponseEntity<Void> deleteEnrolement(@RequestHeader("Authorisation") String  requestHeader,@PathVariable Long enrolementId) {
//        Long id = jwtUtil.extractId(requestHeader.replace("Bearer ",""));
//        return ResponseEntity.ok(id,enrolementId);
//    }
}
