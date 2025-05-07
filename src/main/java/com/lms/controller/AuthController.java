package com.lms.controller;

import com.lms.dto.auth.request.LoginRequest;
import com.lms.dto.auth.request.RegisterRequest;
import com.lms.dto.auth.response.LoginResponse;
import com.lms.dto.auth.response.RegisterResponse;
import com.lms.dto.ApiResponse;
import com.lms.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest newUser) {
        RegisterResponse response = authService.register(newUser);
        ApiResponse<RegisterResponse> apiResponse = new ApiResponse<>("success", "User registered successfully", response);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        ApiResponse<LoginResponse> apiResponse = new ApiResponse<>("success", "Login successful", response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
