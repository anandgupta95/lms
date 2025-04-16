//package com.lms.dto.auth.response;
//
//public class LoginResponse {
//}

package com.lms.dto.auth.response;

public class LoginResponse {

    private Long id;
    private String email;
//    private String role;
    private String token;
    private String username;

    public LoginResponse() {}

    public LoginResponse(Long id, String email,String username, String role, String token) {
        this.id = id;
        this.email = email;
        this.username = username;
//        this.role = role;
        this.token = token;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
