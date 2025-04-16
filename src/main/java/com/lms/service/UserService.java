//package com.lms.service;
//
//import com.lms.dto.user.request.UserRequestDTO;
//import com.lms.dto.user.response.UserResponseDTO;
//import com.lms.mapper.UserMapper;
//import com.lms.model.User;
//import com.lms.repository.UserRepository;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class UserService {
//    private final UserRepository userRepository;
//    private final UserMapper userMapper;
//
//    public UserService(UserRepository userRepository, UserMapper userMapper) {
//        this.userRepository = userRepository;
//        this.userMapper = userMapper;
//    }
//
//    public List<UserResponseDTO> getUser() {
//        return userRepository.findAll()
//                .stream()
//                .map(userMapper::toResponseDto)
//                .toList();
//    }
//
//    public UserResponseDTO getUser(Long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("No User found with id: " + id));
//        return userMapper.toResponseDto(user);
//    }
//
//    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("No User found with id: " + id));
//
//        user.setFirstName(userRequestDTO.getFirstName());
//        user.setLastName(userRequestDTO.getLastName());
////        user.setRole(userRequestDTO.getRole());
//        User updatedUser = userRepository.save(user);
//        return userMapper.toResponseDto(updatedUser);
//    }
//
//
//    public void deleteUser(Long id) {
//        userRepository.deleteById(id);
//    }
//}
//
