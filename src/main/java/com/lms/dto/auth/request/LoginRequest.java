//package com.lms.dto.auth.request;
//
//public class LoginRequest {
//}

package com.lms.dto.auth.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
//
//    @Email(message = "Invalid email format")
//    @NotBlank(message = "Email is mandatory")
    private String email;

//    @Email(message = "Invalid email format")
//    @NotBlank(message = "username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //     Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

