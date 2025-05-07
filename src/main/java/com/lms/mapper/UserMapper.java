
package com.lms.mapper;

import com.lms.dto.user.UserResponse;
import com.lms.model.Auth;
import com.lms.model.Student;
import com.lms.model.Teacher;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toDto(Auth user, Student student){
        UserResponse userResponse = new UserResponse();

        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());

        userResponse.setId(student.getId());
        userResponse.setAddress(student.getAddress());
        userResponse.setFullName(student.getFullName());
        userResponse.setPhoneNumber(student.getPhoneNumber());

        return userResponse;
    }
    public UserResponse toDto(Auth user, Teacher teacher){
        UserResponse userResponse = new UserResponse();

        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());

        userResponse.setId(teacher.getId());
        userResponse.setAddress(teacher.getAddress());
        userResponse.setFullName(teacher.getFullName());
        userResponse.setPhoneNumber(teacher.getPhoneNumber());

        return userResponse;
    }


}

