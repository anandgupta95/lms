package com.lms.interceptor;

import com.lms.annotation.RequiredRole;
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

        String userRole = (String) request.getAttribute("userRole");

        if (userRole == null || !userRole.equals(requiredRole.value().name())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Forbidden: Insufficient role!");
            return false;
        }
        return true;
    }
}

