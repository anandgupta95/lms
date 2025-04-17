package com.lms.mapper;


import com.lms.dto.auth.request.LoginRequest;
import com.lms.dto.auth.request.RegisterRequest;
import com.lms.dto.auth.response.LoginResponse;
import com.lms.dto.auth.response.RegisterResponse;
import com.lms.model.Auth;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    //for registration
    public Auth toEntity(RegisterRequest dto){

        if(dto == null) return  null;

       Auth auth = new Auth();
       auth.setEmail(dto.getEmail());
       auth.setUsername(dto.getEmail().split("@")[0]);  // for now i am using email id as user name but after i will generate unique username
       auth.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
       auth.setRole(Auth.Role.valueOf(dto.getRole().toUpperCase()));
       return auth;

    }

    public RegisterResponse toRegisterResponseDto(Auth auth){
        if( auth == null) return null;

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setId(auth.getId());
        registerResponse.setUsername(auth.getUsername());
        registerResponse.setEmail(auth.getEmail());
        registerResponse.setMessage("User registered successfully!");
        return registerResponse;
    }

    //for login

//    public Auth toEntity(LoginRequest loginRequest){
//        if(loginRequest == null ) return null;
//        Auth auth = new Auth();
//        auth.setEmail(loginRequest.getUserOrEmail());
//        auth.setPassword(loginRequest.getPassword());
//        return auth;
//    }

    public LoginResponse toLoginResponseDto(Auth auth, String accesstoken){
        if(auth == null) return  null;
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(auth.getId());
        loginResponse.setUsername(auth.getUsername());
        loginResponse.setToken(accesstoken);
        return loginResponse;

    }

    // for response user (auth)
//    public UserResponse toUser

}
