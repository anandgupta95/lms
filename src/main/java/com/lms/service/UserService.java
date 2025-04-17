package com.lms.service;

import com.lms.dto.profile.UpdateProfile;
import com.lms.mapper.ProfileMapper;
//import com.lms.model.User;
//import com.lms.repository.UserRepository;
import com.lms.model.Auth;
import com.lms.model.Student;
import com.lms.model.Teacher;
import com.lms.repository.AuthRepository;
import com.lms.repository.StudentRepository;
import com.lms.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    //Dependencies injections
    private final AuthRepository authRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ProfileMapper profileMapper;

    public UserService( AuthRepository authRepository,
     StudentRepository studentRepository,
     TeacherRepository teacherRepository,
     ProfileMapper profileMapper) {
        this.authRepository = authRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.profileMapper = profileMapper;

    }

//    public List<UserResponseDTO> getUser() {
//        return userRepository.findAll()
//                .stream()
//                .map(userMapper::toResponseDto)
//                .toList();
//    }

//    public UserResponseDTO getUser(Long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("No User found with id: " + id));
//        return userMapper.toResponseDto(user);
//    }

    public UpdateProfile updateProfile(Long id, UpdateProfile updateProfile) {
        Student student = new Student();
        Teacher teacher = new Teacher();
        if(authRepository.countByUsernameNative(updateProfile.getUsername())>1){
            throw new RuntimeException("Username is already used by someone else please try with another email ");
        }
        if(authRepository.countByEmailNative(updateProfile.getEmail())>1){
            throw new RuntimeException("email is already used by someone else please try with another email ");
        }

       authRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No User found with id: " + id));
        Auth user = authRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No User found with id: " + id));

        profileMapper.toAuthEntity(updateProfile,user);
        if(user.getRole().name().equals("STUDENT")){
             student = studentRepository.findByAuthId(id).orElseThrow(()-> new RuntimeException("user not found "));
            profileMapper.toStudentEntity(updateProfile,student);
            studentRepository.save(student);
        }

        if(user.getRole().name().equals("TEACHER")){
           teacher = teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher not found "));
            profileMapper.toTeacherEntity(updateProfile,teacher);
            teacherRepository.save(teacher);
        }

        authRepository.save(user);

        return user.getRole().name().equals("STUDENT")?profileMapper.toDto(user,student):profileMapper.toDto(user,teacher);
    }
//    public void deleteUser(Long id) {
//        userRepository.deleteById(id);
//    }
}

