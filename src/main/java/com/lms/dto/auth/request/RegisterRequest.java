package com.lms.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "Email is mandatory")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Invalid email format"
    )
    private String email;

    @NotBlank(message = "Role is mandatory")
    @Pattern(regexp = "^(STUDENT|TEACHER|ADMIN)$", message = "Role must be STUDENT, TEACHER, or ADMIN")
    private String role;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;

    // Optionally, you could add more fields like fullName, phone, etc. with validations

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
