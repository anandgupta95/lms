
package com.lms.dto.auth.response;

public class LoginResponse {

    private Long id;
    private String email;
    private String token;
    private String username;

    public LoginResponse() {}

    public LoginResponse(Long id, String email,String username, String role, String token) {
        this.id = id;
        this.username = username;
        this.token = token;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String email) {
        this.username = email;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
