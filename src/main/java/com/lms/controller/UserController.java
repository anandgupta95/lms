package com.lms.controller;

import com.lms.annotation.RequiredRole;
//import com.lms.dto.user.request.UserRequestDTO;
import com.lms.dto.profile.UpdateProfile;
import com.lms.model.Auth;
import com.lms.model.Student;
import com.lms.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user")
@RestController
public class UserController {

    UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

//    @PostMapping("/create")
//    public ResponseEntity<?> createUser(@RequestBody User user){
////         return ResponseEntity.ok(userService.createUser(user));
//         return ResponseEntity.status(200).body(userService.createUser(user));
//    }

//    @GetMapping
//    @RequiredRole(User.Role.ADMIN)
//    public ResponseEntity<?> getAllUsers(){
//        return ResponseEntity.ok(userService.getUser());
//    }

//    @GetMapping("/{id}")
//    @RequiredRole(User.Role.ADMIN)
//    public ResponseEntity<?> getUser(@PathVariable Long id){
//        return ResponseEntity.ok(userService.getUser(id));
//    }

    @PutMapping("/{id}")
//    @RequiredRole(Auth.Role.ADMIN)
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody UpdateProfile updateProfile) {
        return ResponseEntity.ok(userService.updateProfile(id, updateProfile));
    }


//    @DeleteMapping("/{id}")
//    @RequiredRole(User.Role.ADMIN)
//    public void deleteUser(@PathVariable Long id){
//        userService.deleteUser(id);
//    }
}
