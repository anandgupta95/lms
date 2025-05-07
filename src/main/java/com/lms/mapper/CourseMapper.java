package com.lms.mapper;

import com.lms.dto.course.request.CourseRequest;
import com.lms.dto.course.response.CourseResponse;
import com.lms.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
    public Course toEntity(CourseRequest courseRequest){
        Course course = new Course();
        course.setPaid(courseRequest.getIsPaid());
        course.setBatch(courseRequest.getBatch());
        course.setPrice(courseRequest.getPrice());
        course.setSubject(courseRequest.getSubject());
        course.setDescription(courseRequest.getDescription());
        course.setUrl(courseRequest.getUrl());
        course.setTitle(courseRequest.getTitle());
        return  course;
    }

    public CourseResponse toDto(Course course){
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setId(course.getId());
        courseResponse.setBatch(course.getBatch());
        courseResponse.setPrice(course.getPrice());
        courseResponse.setSubject(course.getSubject());
        courseResponse.setDescription(course.getDescription());
        courseResponse.setUrl(course.getUrl());
        courseResponse.setTitle(course.getTitle());
        courseResponse.setNoOfEnrolled((long) course.getEnrolementList().size());
        courseResponse.setTaughtBy(course.getTeacher().getFullName());
//        courseResponse.setMessage(message);
        return courseResponse;
    }

}
