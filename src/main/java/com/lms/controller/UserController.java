package com.lms.controller;

import com.lms.annotation.RequiredRole;
import com.lms.dto.user.UpdateProfile;
import com.lms.service.UserService;
import com.lms.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user")
@RestController
public class UserController {

    UserService userService;
    JwtUtil jwtUtil;

    public UserController(UserService userService,JwtUtil jwtUtil){
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }
    


    @GetMapping
    @RequiredRole({"STUDENT","TEACHER"})
    public ResponseEntity<?> getUser(
    @RequestHeader ("Authorization") String requestHeader ){
        String token = requestHeader.replace("Bearer ", "");
        Long userId = jwtUtil.extractId(token);
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping
    @RequiredRole({"STUDENT","TEACHER"})
    public ResponseEntity<?> updateProfile(@RequestHeader ("Authorization") String requestHeader ,@RequestBody UpdateProfile updateProfile) {
        String token = requestHeader.replace("Bearer ", "");
        Long id = jwtUtil.extractId(token);
        return ResponseEntity.ok(userService.updateProfile(id, updateProfile));
    }


    @DeleteMapping
    @RequiredRole({"STUDENT","TEACHER"})
    public void deleteUser(@RequestHeader ("Authorization") String requestHeader ){
        String token = requestHeader.replace("Bearer ", "");
        Long id = jwtUtil.extractId(token);
        userService.deleteUser(id);
    }
}
