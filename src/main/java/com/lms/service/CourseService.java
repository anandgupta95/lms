//package com.lms.service;
//
//import com.lms.dto.course.request.CourseRequest;
//import com.lms.dto.course.response.CourseResponse;
//import com.lms.mapper.CourseMapper;
//import com.lms.model.Course;
//import com.lms.repository.AuthRepository;
//import com.lms.repository.CourseRepository;
//import com.lms.repository.TeacherRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class CourseService {
//
//    private final CourseRepository courseRepository;
//    private final TeacherRepository teacherRepository;
//    private final AuthRepository authRepository;
//    private final CourseMapper courseMapper;
//
//    public CourseService(CourseRepository courseRepository,TeacherRepository teacherRepository,AuthRepository authRepository,CourseMapper courseMapper){
//        this.courseRepository = courseRepository;
//        this.teacherRepository = teacherRepository;
//        this.authRepository = authRepository;
//        this.courseMapper = courseMapper;
//    }
//
//
//    public CourseResponse createCourse(Long id, CourseRequest courseRequest){
//        Course temp = courseMapper.toEntity(courseRequest);
//        //this  line is return id of auth pk as fk in course table but i want teacher pk as fk in course table
////        temp.setTeacher(authRepository.findById(id).get().getTeacher());
//        temp.setTeacher(authRepository.findById(id).orElseThrow().getTeacher());
//        Course course = courseRepository.save(temp);
//        return courseMapper.toDto(course) ;
//    }
//
//
//    public List<CourseResponse> getCourses(){
//        return courseRepository.findAll().stream().map(courseMapper::toDto).toList();
//    }
//
//    //get all courses by teacher
//    public List<Course> getCourses(Long id){
//        return courseRepository.findCoursesByTeacherId(authRepository.findById(id).orElseThrow().getTeacher().getId());
//    }
//
//    public List<CourseResponse> getCourseByBatch(String batch){
//        return courseRepository.findCourseByBatch(batch).stream().map(courseMapper::toDto).toList();
//    }
//    public List<CourseResponse> getCourseBySubject(String subject) {
//        return courseRepository.findCourseBySubject(subject).stream().map(courseMapper::toDto).toList();
//    }
//
//    public CourseResponse updateCourse(Long courseId, Long userId, CourseRequest courseRequest){
//        List<Course> courseList = courseRepository.findCoursesByTeacherId(authRepository.findById(userId).orElseThrow().getTeacher().getId());
//        for(int i=0; i<courseList.size(); i++){
//            Course course =courseList.get(i);
//            if(course.getId().equals(courseId)){
//                course.setPaid(courseRequest.isPaid());
//                course.setUrl(courseRequest.getUrl());
//                course.setTitle(courseRequest.getUrl());
//                course.setDescription(courseRequest.getDescription());
//                course.setPrice(courseRequest.getPrice());
//                course.setSubject(courseRequest.getSubject());
//                course.setBatch(courseRequest.getBatch());
//                return courseMapper.toDto(courseRepository.save(course));
//            }
//        }
//        if(courseRepository.existsById(courseId))
//            throw new RuntimeException("Course doesn't exist with this id : "+courseId);
//        throw new RuntimeException("Access Denied!");
//    }
//
//
//    public String deleteCourse(Long userId, Long courseId){
//        List<Course> courseList = courseRepository.findCoursesByTeacherId(authRepository.findById(userId).orElseThrow().getTeacher().getId());
//        for(int i =0; i<courseList.size(); i++){
//            if (courseList.get(i).getId().equals(courseId))
//            {
//                courseRepository.deleteById(courseId);
//                return "Course deleted successfully";
//            }
//        }
//        if(courseRepository.existsById(courseId))
//            return "Course is not Exist by given id :" +courseId;
//         return "Access denied";
//
//    }
//
//
//
//}

package com.lms.service;

import com.lms.dto.course.request.CourseRequest;
import com.lms.dto.course.response.CourseResponse;
import com.lms.exception.course.CourseNotFoundException;
import com.lms.exception.auth.UnauthorizedException;
import com.lms.exception.user.UserNotFoundException;
import com.lms.mapper.CourseMapper;
import com.lms.model.Course;
import com.lms.model.Teacher;
import com.lms.repository.AuthRepository;
import com.lms.repository.CourseRepository;
import com.lms.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final AuthRepository authRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, TeacherRepository teacherRepository,
                         AuthRepository authRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.authRepository = authRepository;
        this.courseMapper = courseMapper;
    }

    public CourseResponse createCourse(Long userId, CourseRequest courseRequest) {
        Teacher teacher = authRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId))
                .getTeacher();

        if (teacher == null) {
            throw new UserNotFoundException("Teacher not found for user id: " + userId);
        }

        Course course = courseMapper.toEntity(courseRequest);
        course.setTeacher(teacher);
        return courseMapper.toDto(courseRepository.save(course));
    }

    public List<CourseResponse> getCourses() {
        return courseRepository.findAll().stream().map(courseMapper::toDto).toList();
    }

    public List<Course> getCourses(Long userId) {
        Teacher teacher = authRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId))
                .getTeacher();

        if (teacher == null) {
            throw new UserNotFoundException("Teacher not found for user id: " + userId);
        }

        return courseRepository.findCoursesByTeacherId(teacher.getId());
    }

    public List<CourseResponse> getCourseByBatch(String batch) {
        return courseRepository.findCourseByBatch(batch)
                .stream()
                .map(courseMapper::toDto)
                .toList();
    }

    public List<CourseResponse> getCourseBySubject(String subject) {
        return courseRepository.findCourseBySubject(subject)
                .stream()
                .map(courseMapper::toDto)
                .toList();
    }

    public CourseResponse updateCourse(Long courseId, Long userId, CourseRequest courseRequest) {
        Teacher teacher = authRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId))
                .getTeacher();

        if (teacher == null) {
            throw new UserNotFoundException("Teacher not found for user id: " + userId);
        }

        List<Course> courses = courseRepository.findCoursesByTeacherId(teacher.getId());

        for (Course course : courses) {
            if (course.getId().equals(courseId)) {
                course.setPaid(courseRequest.getIsPaid());
                course.setUrl(courseRequest.getUrl());
                course.setTitle(courseRequest.getTitle()); // corrected from getUrl()
                course.setDescription(courseRequest.getDescription());
                course.setPrice(courseRequest.getPrice());
                course.setSubject(courseRequest.getSubject());
                course.setBatch(courseRequest.getBatch());
                return courseMapper.toDto(courseRepository.save(course));
            }
        }

        if (courseRepository.existsById(courseId)) {
            throw new UnauthorizedException("You do not have access to update this course");
        } else {
            throw new CourseNotFoundException("Course not found with id: " + courseId);
        }
    }

    public String deleteCourse(Long userId, Long courseId) {
        Teacher teacher = authRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId))
                .getTeacher();

        if (teacher == null) {
            throw new UserNotFoundException("Teacher not found for user id: " + userId);
        }

        List<Course> courses = courseRepository.findCoursesByTeacherId(teacher.getId());

        for (Course course : courses) {
            if (course.getId().equals(courseId)) {
                courseRepository.deleteById(courseId);
                return "Course deleted successfully";
            }
        }

        if (courseRepository.existsById(courseId)) {
            throw new UnauthorizedException("You do not have access to delete this course");
        } else {
            throw new CourseNotFoundException("Course not found with id: " + courseId);
        }
    }
}

