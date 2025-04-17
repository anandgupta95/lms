
package com.lms.filter;

import com.lms.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import java.io.IOException;


@Component
public class JwtFilter implements Filter {
    JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            // Extract info from token (assuming you decode and verify here).

            String userRole = jwtUtil.extractRole(token);
            httpRequest.setAttribute("userRole", userRole);
        }

        chain.doFilter(request, response);
    }
}

