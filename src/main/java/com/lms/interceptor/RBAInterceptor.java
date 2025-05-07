package com.lms.interceptor;

import com.lms.annotation.RequiredRole;
import com.lms.model.Auth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RBAInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) return true;  // Skip static resources

        HandlerMethod method = (HandlerMethod) handler;
        RequiredRole requiredRole = method.getMethodAnnotation(RequiredRole.class);


        if (requiredRole == null) return true;  // No restriction
        String[] allowedRoles = requiredRole.value();
        String userRole = (String) request.getAttribute("userRole");
        Long userId = (Long) request.getAttribute("userId");


        boolean hasRole = false;

        for(String role : allowedRoles){
            if(userRole != null && userRole.equals(role)){
                hasRole = true;
                break;
            }
        }

        if (!hasRole) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Forbidden: Insufficient role!");
            return false;
        }
        return true;
    }
}

