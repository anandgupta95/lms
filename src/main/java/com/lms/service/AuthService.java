package com.lms.service;

import com.lms.dto.auth.request.LoginRequest;
import com.lms.dto.auth.request.RegisterRequest;
import com.lms.dto.auth.response.LoginResponse;
import com.lms.dto.auth.response.RegisterResponse;
import com.lms.exception.user.*;
import com.lms.exception.system.InvalidInputException;
import com.lms.mapper.AuthMapper;
import com.lms.model.Auth;
import com.lms.model.Student;
import com.lms.model.Teacher;
import com.lms.repository.AuthRepository;
import com.lms.repository.StudentRepository;
import com.lms.repository.TeacherRepository;
import com.lms.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final AuthMapper authMapper;
    private final JwtUtil jwtUtil;
    private final EduPointService eduPointService;

    public AuthService(AuthRepository authRepository, JwtUtil jwtUtil, StudentRepository studentRepository, AuthMapper authMapper, TeacherRepository teacherRepository, EduPointService eduPointService) {
        this.authRepository = authRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.authMapper = authMapper;
        this.jwtUtil = jwtUtil;
        this.eduPointService = eduPointService;
    }

    public RegisterResponse register(RegisterRequest registerRequest) {
        if (authRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists: " + registerRequest.getEmail());
        }

        Auth savedAuth = authRepository.save(authMapper.toEntity(registerRequest));

        switch (savedAuth.getRole().name()) {
            case "STUDENT":
                Student student = new Student();
                student.setAuth(savedAuth);
                studentRepository.save(student);
                eduPointService.createEduPoint(student);
                break;

            case "TEACHER":
                Teacher teacher = new Teacher();
                teacher.setAuth(savedAuth);
                teacherRepository.save(teacher);
                break;

            default:
                throw new RoleNotAllowedException("Unsupported role: " + savedAuth.getRole().name());
        }

        return authMapper.toRegisterResponseDto(savedAuth);
    }

    public LoginResponse login(LoginRequest loginRequest) {

        if ((loginRequest.getEmail() == null || loginRequest.getEmail().isBlank()) &&
                (loginRequest.getUsername() == null || loginRequest.getUsername().isBlank())) {
            throw new InvalidInputException("Username or Email must be provided");
        }

        Auth auth;

        if (loginRequest.getEmail() != null && !loginRequest.getEmail().isBlank()) {
            auth = authRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("User not found with email: " + loginRequest.getEmail()));
        } else {
            auth = authRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new UserNotFoundException("User not found with username: " + loginRequest.getUsername()));
        }

        if (!BCrypt.checkpw(loginRequest.getPassword(), auth.getPassword())) {
            throw new InvalidCredentialsException("Invalid username/email or password");
        }

        String accessToken = jwtUtil.generateToken(
                auth.getId(),
                auth.getUsername(),
                auth.getRole().name(),
                false
        );

        return authMapper.toLoginResponseDto(auth, accessToken);
    }
}

