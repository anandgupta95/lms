package com.lms.controller;

import com.lms.annotation.RequiredRole;
import com.lms.dto.course.request.CourseRequest;
import com.lms.model.Auth;
import com.lms.model.Course;
import com.lms.service.CourseService;
import com.lms.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/course")
@RestController
public class CourseController {

    CourseService courseService;
    JwtUtil jwtUtil;
    public CourseController(CourseService courseService, JwtUtil jwtUtil){
        this.courseService = courseService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    @RequiredRole({"TEACHER"})
    public ResponseEntity<?> createCourse(@RequestHeader ("Authorization") String requestHeader ,@RequestBody CourseRequest courseRequest){
       return ResponseEntity.ok(courseService.createCourse(jwtUtil.extractId(requestHeader.replace("Bearer ","")),courseRequest));
    }

    @GetMapping("/teacher-courses")
    @RequiredRole({"TEACHER"})
    public ResponseEntity<?> getAllCoursesByTeacher(@RequestHeader("Authorization") String requestHeader) {
        return ResponseEntity.ok(courseService.getCourses(jwtUtil.extractId(requestHeader.replace("Bearer ",""))));
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        return ResponseEntity.ok(courseService.getCourses());
    }
//    @GetMapping("/{enrolledId}")
//    @RequiredRole({"STUDENT"})
//    public ResponseEntity<?> getCourse(@PathVariable Long id){
//        return ResponseEntity.ok(courseService.getCourse(id));
//    }
    @GetMapping("/batch")
    @RequiredRole({"STUDENT","ADMIN"})
    public ResponseEntity<?> getCourseByBatch(@RequestBody String batch){
        return ResponseEntity.ok(courseService.getCourseByBatch(batch));
    }
    @GetMapping("/subject")
    @RequiredRole({"STUDENT","SUBJECT"})
    public ResponseEntity<?> getCourseBySubject(@RequestBody String subject){
        return ResponseEntity.ok(courseService.getCourseBySubject(subject));
    }


    @PutMapping("/{courseId}")
    @RequiredRole({"TEACHER"})
    public ResponseEntity<?> updateCourse(@RequestHeader ("Authorization") String requestHeader, @PathVariable Long courseId, @RequestBody CourseRequest courseRequest){
        return ResponseEntity.ok(courseService.updateCourse(courseId, jwtUtil.extractId(requestHeader.replace("Bearer ","")),courseRequest));
    }

    @DeleteMapping("/{id}")
    @RequiredRole({"TEACHER"})
    public String delete(@RequestHeader("Authorization") String  requestHeader,@PathVariable Long id){
      return  courseService.deleteCourse(jwtUtil.extractId(requestHeader.replace("Bearer ","")),id);
    }
}
