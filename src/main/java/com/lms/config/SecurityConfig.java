//package com.lms.config;
//
//import org.springframework.context.annotation.Bean;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.HttpSecurityDsl;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//public class
//
//
//
//
//SecurityConfig {
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http
//                .csrf(csrf -> csrf.disable())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/auth/**").permitAll()
//                        .requestMatchers("/api/student/").hasRole("STUDENT")
//                        .requestMatchers("/api/courses/").hasAnyRole("ADMIN","STUDENT","TEACHER")
//                        .anyRequest().authenticated())
//                        .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
//}
