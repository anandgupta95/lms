//
//package com.lms.mapper;
//
//import com.lms.dto.student.request.UserRequestDTO;
//import com.lms.dto.student.response.UserResponseDTO;
//import com.lms.model.Auth;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserMapper {
//    public UserResponseDTO toResponseDto(Auth user) {
//        if (user == null) return null;
//        UserResponseDTO dto = new UserResponseDTO();
//        dto.setId(user.getId());
//        dto.setFirstName(user.getFirstName());
//        dto.setLastName(user.getLastName());
//        dto.setRole(user.getRole());
//        return dto;
//    }
//
//    public Auth toEntity(UserRequestDTO dto) {
//        if (dto == null) return null;
//        Auth user = new Auth();
//        user.setFirstName(dto.getFirstName());
//        user.setLastName(dto.getLastName());
//        user.setRole(dto.getRole());
//        return user;
//    }
//
//}
//
