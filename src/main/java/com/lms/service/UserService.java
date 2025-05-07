//package com.lms.service;
//
//import com.lms.dto.user.UpdateProfile;
//import com.lms.dto.user.UserResponse;
//import com.lms.mapper.ProfileMapper;
//import com.lms.mapper.UserMapper;
//import com.lms.model.Auth;
//import com.lms.model.Student;
//import com.lms.model.Teacher;
//import com.lms.repository.AuthRepository;
//import com.lms.repository.StudentRepository;
//import com.lms.repository.TeacherRepository;
//import com.lms.util.JwtUtil;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class UserService {
//
//    //Dependencies injections
//    private final AuthRepository authRepository;
//    private final StudentRepository studentRepository;
//    private final TeacherRepository teacherRepository;
//    private final ProfileMapper profileMapper;
//    private final UserMapper userMapper;
//
//    public UserService( AuthRepository authRepository, StudentRepository studentRepository, TeacherRepository teacherRepository, ProfileMapper profileMapper, UserMapper userMapper) {
//        this.authRepository = authRepository;
//        this.studentRepository = studentRepository;
//        this.teacherRepository = teacherRepository;
//        this.profileMapper = profileMapper;
//        this.userMapper = userMapper;
//
//    }
////    63get user by role
////    get user by id
////    public List<UserResponseDTO> getUser() {
////        return userRepository.findAll()
////                .stream()
////                .map(userMapper::toResponseDto)
////                .toList();
////    }
//
//    public UserResponse getUser(Long id) {
//        Auth user = authRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("No User found with id: " + id));
//    return user.getRole().name().equals("STUDENT")
//            ?userMapper.toDto(user,studentRepository.findByAuthId(id).orElseThrow(()-> new RuntimeException("user not found ")))
//            :userMapper.toDto(user,teacherRepository.findByAuthId(id).orElseThrow(()-> new RuntimeException("user not found ")));
//    }
//
//    public UpdateProfile updateProfile(Long id, UpdateProfile updateProfile) {
//        Student student = new Student();
//        Teacher teacher = new Teacher();
//        if(authRepository.countByUsernameNative(updateProfile.getUsername())>1){
//            throw new RuntimeException("Username is already used by someone else please try with another email ");
//        }
//        if(authRepository.countByEmailNative(updateProfile.getEmail())>1){
//            throw new RuntimeException("email is already used by someone else please try with another email ");
//        }
//
//       authRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("No User found with id: " + id));
//        Auth user = authRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("No User found with id: " + id));
//
//        profileMapper.toAuthEntity(updateProfile,user);
//        if(user.getRole().name().equals("STUDENT")){
//             student = studentRepository.findByAuthId(id).orElseThrow(()-> new RuntimeException("user not found "));
//            profileMapper.toStudentEntity(updateProfile,student);
//            studentRepository.save(student);
//        }
//
//        if(user.getRole().name().equals("TEACHER")){
//           teacher = teacherRepository.findByAuthId(id).orElseThrow(() -> new RuntimeException("Teacher not found "));
//            profileMapper.toTeacherEntity(updateProfile,teacher);
//            teacherRepository.save(teacher);
//        }
//
//        authRepository.save(user);
//
//        return user.getRole().name().equals("STUDENT")
//                ?profileMapper.toDto(user,student)
//                :profileMapper.toDto(user,teacher);
//    }
//
////    admin and user can delete
//    public void deleteUser(Long id) {
//        authRepository.deleteById(id);
//    }
//}
//

package com.lms.service;

import com.lms.dto.user.UpdateProfile;
import com.lms.dto.user.UserResponse;
import com.lms.exception.user.UserAlreadyExistsException;
import com.lms.mapper.ProfileMapper;
import com.lms.mapper.UserMapper;
import com.lms.model.Auth;
import com.lms.model.Student;
import com.lms.model.Teacher;
import com.lms.repository.AuthRepository;
import com.lms.repository.StudentRepository;
import com.lms.repository.TeacherRepository;
import com.lms.exception.user.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final AuthRepository authRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ProfileMapper profileMapper;
    private final UserMapper userMapper;

    public UserService(AuthRepository authRepository, StudentRepository studentRepository, TeacherRepository teacherRepository, ProfileMapper profileMapper, UserMapper userMapper) {
        this.authRepository = authRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.profileMapper = profileMapper;
        this.userMapper = userMapper;
    }

    public UserResponse getUser(Long id) {
        Auth user = authRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No User found with id: " + id));

        if (user.getRole() == Auth.Role.STUDENT) {
            Student student = studentRepository.findByAuthId(id)
                    .orElseThrow(() -> new UserNotFoundException("Student not found with id: " + id));
            return userMapper.toDto(user, student);
        } else if (user.getRole() == Auth.Role.TEACHER) {
            Teacher teacher = teacherRepository.findByAuthId(id)
                    .orElseThrow(() -> new UserNotFoundException("Teacher not found with id: " + id));
            return userMapper.toDto(user, teacher);
        } else {
            throw new UserNotFoundException("Unsupported role for user with id: " + id);
        }
    }

    @Transactional
    public UpdateProfile updateProfile(Long id, UpdateProfile updateProfile) {
        // Check if the username or email already exists
        if (authRepository.countByUsernameNative(updateProfile.getUsername()) > 0) {
            throw new UserAlreadyExistsException("Username is already used. Please try with another username.");
        }
        if (authRepository.countByEmailNative(updateProfile.getEmail()) > 0) {
            throw new UserAlreadyExistsException("Email is already used. Please try with another email.");
        }

        // Retrieve user from the repository
        Auth user = authRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No User found with id: " + id));

        // Update profile details in Auth
        profileMapper.toAuthEntity(updateProfile, user);

        if (user.getRole() == Auth.Role.STUDENT) {
            Student student = studentRepository.findByAuthId(id)
                    .orElseThrow(() -> new UserNotFoundException("Student not found with id: " + id));
            profileMapper.toStudentEntity(updateProfile, student);
            studentRepository.save(student);
        } else if (user.getRole() == Auth.Role.TEACHER) {
            Teacher teacher = teacherRepository.findByAuthId(id)
                    .orElseThrow(() -> new UserNotFoundException("Teacher not found with id: " + id));
            profileMapper.toTeacherEntity(updateProfile, teacher);
            teacherRepository.save(teacher);
        } else {
            throw new UserNotFoundException("Unsupported role for user with id: " + id);
        }

        // Save the updated user entity
        authRepository.save(user);

        if (user.getRole() == Auth.Role.STUDENT) {
            return profileMapper.toDto(user, studentRepository.findByAuthId(id)
                    .orElseThrow(() -> new UserNotFoundException("Student not found with id: " + id)));
        } else if (user.getRole() == Auth.Role.TEACHER) {
            return profileMapper.toDto(user, teacherRepository.findByAuthId(id)
                    .orElseThrow(() -> new UserNotFoundException("Teacher not found with id: " + id)));
        } else {
            throw new UserNotFoundException("Unsupported role for user with id: " + id);
        }
    }

    public void deleteUser(Long id) {
        // Ensure the user exists before attempting deletion
        if (!authRepository.existsById(id)) {
            throw new UserNotFoundException("No User found with id: " + id);
        }
        authRepository.deleteById(id);
    }
}

