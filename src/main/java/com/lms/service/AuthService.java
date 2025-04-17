
package com.lms.service;

import com.lms.dto.auth.request.LoginRequest;
import com.lms.dto.auth.request.RegisterRequest;
import com.lms.dto.auth.response.LoginResponse;
import com.lms.dto.auth.response.RegisterResponse;
import com.lms.mapper.AuthMapper;
import com.lms.model.Auth;
import com.lms.model.Student;
import com.lms.model.Teacher;
import com.lms.repository.AuthRepository;
import com.lms.repository.StudentRepository;
import com.lms.repository.TeacherRepository;
import com.lms.util.JwtUtil;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

//    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final AuthMapper authMapper;
    private final JwtUtil jwtUtil;

    public AuthService(AuthRepository authRepository, JwtUtil jwtUtil,StudentRepository studentRepository,AuthMapper authMapper,TeacherRepository teacherRepository) {
        this.authRepository = authRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.authMapper = authMapper;
        this.jwtUtil = jwtUtil;
    }

    public RegisterResponse register(RegisterRequest registerRequest) throws BadRequestException {
        if (authRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists!");
        }
        Auth savedAuth = authRepository.save(authMapper.toEntity(registerRequest));
        // Create Student
        if(savedAuth.getRole().name().equals("STUDENT")){
            Student student = new Student();
            student.setAuth(savedAuth);
            studentRepository.save(student);
        }
       if(savedAuth.getRole().name().equals("TEACHER")){
            Teacher teacher = new Teacher();
            teacher.setAuth(savedAuth);
            teacherRepository.save(teacher);
        }
        return authMapper.toRegisterResponseDto(savedAuth);
    }

    public LoginResponse login(LoginRequest loginRequest) {

        Auth auth;

        if (loginRequest.getEmail() != null && !loginRequest.getEmail().isBlank()) {
            // Search by email if provided
            auth = authRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + loginRequest.getEmail()));
        } else if (loginRequest.getUsername() != null && !loginRequest.getUsername().isBlank()) {
            // Search by username if email is empty
            auth = authRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found with username: " + loginRequest.getUsername()));
        } else {
            throw new RuntimeException("Username or Email must be provided");
        }

        // Validate password
        if (!BCrypt.checkpw(loginRequest.getPassword(), auth.getPassword())) {
            throw new RuntimeException("Invalid username/email or password");
        }

        // Generate JWT token
        String accessToken = jwtUtil.generateToken(
                auth.getUsername(),
                auth.getRole().name(),
                false
        );

        // Map to response DTO
        return authMapper.toLoginResponseDto(auth, accessToken);
    }

}
