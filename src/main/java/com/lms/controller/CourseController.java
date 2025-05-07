//
//package com.lms.controller;
//
//import com.lms.annotation.RequiredRole;
//import com.lms.dto.ApiResponse;
//import com.lms.dto.course.request.CourseRequest;
//import com.lms.service.CourseService;
//import com.lms.util.JwtUtil;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RequestMapping("/api/course")
//@RestController
//public class CourseController {
//
//    private final CourseService courseService;
//    private final JwtUtil jwtUtil;
//
//    public CourseController(CourseService courseService, JwtUtil jwtUtil) {
//        this.courseService = courseService;
//        this.jwtUtil = jwtUtil;
//    }
//
//    @PostMapping
//    @RequiredRole({"TEACHER"})
//    public ResponseEntity<ApiResponse<?>> createCourse(@RequestHeader("Authorization") String requestHeader, @RequestBody CourseRequest courseRequest) {
//        Long userId = jwtUtil.extractId(requestHeader.replace("Bearer ", ""));
//        return ResponseEntity.ok(new ApiResponse<>("success", "Course created successfully", courseService.createCourse(userId, courseRequest)));
//    }
//
//    @GetMapping("/teacher-courses")
//    @RequiredRole({"TEACHER"})
//    public ResponseEntity<ApiResponse<?>> getAllCoursesByTeacher(@RequestHeader("Authorization") String requestHeader) {
//        Long userId = jwtUtil.extractId(requestHeader.replace("Bearer ", ""));
//        return ResponseEntity.ok(new ApiResponse<>("success", "Courses fetched successfully", courseService.getCourses(userId)));
//    }
//
//    @GetMapping
//    public ResponseEntity<ApiResponse<?>> getAllCourses() {
//        return ResponseEntity.ok(new ApiResponse<>("success", "Courses fetched successfully", courseService.getCourses()));
//    }
//
//    @GetMapping("/batch")
//    @RequiredRole({"STUDENT", "ADMIN"})
//    public ResponseEntity<ApiResponse<?>> getCourseByBatch(@RequestBody String batch) {
//        return ResponseEntity.ok(new ApiResponse<>("success", "Courses fetched by batch successfully", courseService.getCourseByBatch(batch)));
//    }
//
//    @GetMapping("/subject")
//    @RequiredRole({"STUDENT", "SUBJECT"})
//    public ResponseEntity<ApiResponse<?>> getCourseBySubject(@RequestBody String subject) {
//        return ResponseEntity.ok(new ApiResponse<>("success", "Courses fetched by subject successfully", courseService.getCourseBySubject(subject)));
//    }
//
//    @PutMapping("/{courseId}")
//    @RequiredRole({"TEACHER"})
//    public ResponseEntity<ApiResponse<?>> updateCourse(@RequestHeader("Authorization") String requestHeader, @PathVariable Long courseId, @RequestBody CourseRequest courseRequest) {
//        Long userId = jwtUtil.extractId(requestHeader.replace("Bearer ", ""));
//        return ResponseEntity.ok(new ApiResponse<>("success", "Course updated successfully", courseService.updateCourse(courseId, userId, courseRequest)));
//    }
//
//    @DeleteMapping("/{id}")
//    @RequiredRole({"TEACHER"})
//    public ResponseEntity<ApiResponse<?>> delete(@RequestHeader("Authorization") String requestHeader, @PathVariable Long id) {
//        Long userId = jwtUtil.extractId(requestHeader.replace("Bearer ", ""));
//        return ResponseEntity.ok(new ApiResponse<>("success", "Course deleted successfully", courseService.deleteCourse(userId, id)));
//    }
//}

package com.lms.controller;

import com.lms.annotation.RequiredRole;
import com.lms.dto.ApiResponse;
import com.lms.dto.course.request.CourseRequest;
import com.lms.service.CourseService;
import com.lms.util.JwtUtil;
import com.lms.exception.course.CourseNotFoundException;
import com.lms.exception.course.DuplicateCourseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/course")
@RestController
public class CourseController {

    private final CourseService courseService;
    private final JwtUtil jwtUtil;

    public CourseController(CourseService courseService, JwtUtil jwtUtil) {
        this.courseService = courseService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    @RequiredRole({"TEACHER"})
    public ResponseEntity<ApiResponse<?>> createCourse(@RequestHeader("Authorization") String requestHeader, @RequestBody CourseRequest courseRequest) {
        try {
            Long userId = jwtUtil.extractId(requestHeader.replace("Bearer ", ""));
            return ResponseEntity.ok(new ApiResponse<>("success", "Course created successfully", courseService.createCourse(userId, courseRequest)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>("error", "Internal server error", null));
        }
    }

    @GetMapping("/teacher-courses")
    @RequiredRole({"TEACHER"})
    public ResponseEntity<ApiResponse<?>> getAllCoursesByTeacher(@RequestHeader("Authorization") String requestHeader) {
        try {
            Long userId = jwtUtil.extractId(requestHeader.replace("Bearer ", ""));
            return ResponseEntity.ok(new ApiResponse<>("success", "Courses fetched successfully", courseService.getCourses(userId)));
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllCourses() {
        try {
            return ResponseEntity.ok(new ApiResponse<>("success", "Courses fetched successfully", courseService.getCourses()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>("error", "Internal server error", null));
        }
    }

    @PutMapping("/{courseId}")
    @RequiredRole({"TEACHER"})
    public ResponseEntity<ApiResponse<?>> updateCourse(@RequestHeader("Authorization") String requestHeader, @PathVariable Long courseId, @RequestBody CourseRequest courseRequest) {
        try {
            Long userId = jwtUtil.extractId(requestHeader.replace("Bearer ", ""));
            return ResponseEntity.ok(new ApiResponse<>("success", "Course updated successfully", courseService.updateCourse(courseId, userId, courseRequest)));
        } catch (DuplicateCourseException e) {
            return ResponseEntity.status(409).body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    @RequiredRole({"TEACHER"})
    public ResponseEntity<ApiResponse<?>> deleteCourse(@RequestHeader("Authorization") String requestHeader, @PathVariable Long id) {
        try {
            Long userId = jwtUtil.extractId(requestHeader.replace("Bearer ", ""));
            return ResponseEntity.ok(new ApiResponse<>("success", "Course deleted successfully", courseService.deleteCourse(userId, id)));
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }
}

