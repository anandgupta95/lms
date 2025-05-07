
package com.lms.controller;

import com.lms.annotation.RequiredRole;
import com.lms.dto.ApiResponse;
import com.lms.model.Enrolement;
import com.lms.service.EnrolementService;
import com.lms.util.JwtUtil;
import com.lms.exception.enrollment.AlreadyEnrolledException;
import com.lms.exception.course.CourseNotFoundException;
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
    public EnrolementController(EnrolementService enrolementService, JwtUtil jwtUtil) {
        this.enrolementService = enrolementService;
        this.jwtUtil = jwtUtil;
    }

    @RequiredRole({"STUDENT"})
    @PostMapping("/{courseId}")
    public ResponseEntity<ApiResponse<?>> createEnrolement(@RequestHeader("Authorization") String requestHeader, @PathVariable Long courseId) {
        try {
            Long studentId = jwtUtil.extractId(requestHeader.replace("Bearer ", ""));
            return ResponseEntity.ok(new ApiResponse<>("success", "Enrolment created successfully", enrolementService.createEnrolement(studentId, courseId)));
        } catch (AlreadyEnrolledException e) {
            return ResponseEntity.status(409).body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }

    @RequiredRole({"ADMIN"})
    @GetMapping
    public ResponseEntity<ApiResponse<List<Enrolement>>> getAllEnrolements() {
        try {
            return ResponseEntity.ok(new ApiResponse<>("error", "Internal server error", enrolementService.getAllEnrolement()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>("error", "Internal server error", null));
        }
    }
}

