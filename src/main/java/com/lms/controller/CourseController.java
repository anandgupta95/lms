package com.lms.controller;

import com.lms.annotation.RequiredRole;
import com.lms.model.Auth;
import com.lms.model.Course;
import com.lms.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/course")
@RestController
public class CourseController {

    CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @PostMapping("/{teacherId}")
    @RequiredRole(Auth.Role.TEACHER)
    public ResponseEntity<?> createCourse(@PathVariable Long teacherId, @RequestBody Course course){
       return ResponseEntity.ok(courseService.createCourse(teacherId,course));
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses()
    {
        return ResponseEntity.ok(courseService.getCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourse(@PathVariable Long id){
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PutMapping("/{courseId}/{teacherId}")
    public ResponseEntity<?> updateCourse(@PathVariable Long courseId, @PathVariable Long teacherId, @RequestBody Course course){
        return ResponseEntity.ok(courseService.updateCourse(courseId, teacherId,course));
    }



    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        courseService.deleteCourse(id);
    }
}
