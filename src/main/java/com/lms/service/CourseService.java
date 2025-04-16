package com.lms.service;

import com.lms.model.Course;
import com.lms.repository.CourseRepository;
import com.lms.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public CourseService(CourseRepository courseRepository,TeacherRepository teacherRepository){
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
    }

    public  Course createCourse(long teacherId, Course course){
        if(!teacherRepository.existsById(teacherId)){
            throw new EntityNotFoundException("There is no teacher with this " +teacherId);
        }
        else{
            return courseRepository.save(course);
        }
    }

    public List<Course> getCourses(){
        return courseRepository.findAll();
    }

    public Course getCourseById(long id){
       return courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No course found with this : "+ id));
    }

    public Course updateCourse(long courseId, long teacherId, Course courseDetails){

      if(!courseRepository.existsById(courseId)){
          throw new EntityNotFoundException("No course found with this : " + courseId);
      }

      Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("No course found with this : "+courseId));

      if(!teacherRepository.existsById(teacherId)){
          throw new EntityNotFoundException("No such teacher with this :" + teacherId);
      }
      else if(course.getTeacher().getId() == teacherId){
          course.setTitle(courseDetails.getTitle());
          return courseRepository.save(course);
      }
      else{
          throw new EntityNotFoundException("Teacher don't have permisson to update this " + courseId + "course");
      }
    }


    public void deleteCourse(long id){
        courseRepository.deleteById(id);
    }
}
