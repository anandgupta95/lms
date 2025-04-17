
package com.lms.controller;

import com.lms.dto.auth.request.LoginRequest;
import com.lms.dto.auth.request.RegisterRequest;
import com.lms.dto.auth.response.LoginResponse;
import com.lms.dto.auth.response.RegisterResponse;
import com.lms.service.AuthService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
//            @valid
        @RequestBody RegisterRequest newUser) throws BadRequestException {
        RegisterResponse response = authService.register(newUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
//            @valid
            @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}

