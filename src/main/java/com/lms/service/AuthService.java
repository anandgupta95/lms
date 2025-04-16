
package com.lms.service;

import com.lms.dto.auth.request.LoginRequest;
import com.lms.dto.auth.request.RegisterRequest;
import com.lms.dto.auth.response.LoginResponse;
import com.lms.dto.auth.response.RegisterResponse;
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
    private final JwtUtil jwtUtil;

    public AuthService(AuthRepository authRepository, JwtUtil jwtUtil,StudentRepository studentRepository,TeacherRepository teacherRepository) {
        this.authRepository = authRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.jwtUtil = jwtUtil;
    }

    public RegisterResponse register(RegisterRequest registerRequest) throws BadRequestException {
        if (authRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists!");
        }

        Auth newAuth = new Auth();
        newAuth.setEmail(registerRequest.getEmail());
        newAuth.setUsername(registerRequest.getEmail().split("@")[0]);  // for now i am using email id as user name but after i will generate unique username
        newAuth.setPassword(BCrypt.hashpw(registerRequest.getPassword(), BCrypt.gensalt()));
        newAuth.setRole(Auth.Role.valueOf(registerRequest.getRole().toUpperCase()));
        Auth savedAuth = authRepository.save(newAuth);


        // Create Student
        if(savedAuth.getRole().name().equals("STUDENT")){
            Student student = new Student();
            student.setAuth(savedAuth);
            studentRepository.save(student);
        }
        else if(savedAuth.getRole().name().equals("TEACHER")){
            Teacher teacher = new Teacher();
            teacher.setAuth(savedAuth);
            teacherRepository.save(teacher);
        }


        RegisterResponse response = new RegisterResponse();
        response.setId(savedAuth.getId());
        response.setEmail(savedAuth.getEmail());
        response.setUsername(savedAuth.getUsername());
        response.setMessage("User registered successfully!");

        return response;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Auth auth = authRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Auth user = authRepository.findByAuthId(auth.getId())
                .orElseThrow(() -> new RuntimeException("User role not found"));

        if (!BCrypt.checkpw(loginRequest.getPassword(), auth.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String accessToken = jwtUtil.generateToken(auth.getUsername(), user.getRole().name(), false);

        LoginResponse response = new LoginResponse();
        response.setToken(accessToken);
        response.setId(auth.getId());
        response.setUsername(auth.getUsername());
//        response.setRole(user.getRole().name());

        return response;
    }
}
