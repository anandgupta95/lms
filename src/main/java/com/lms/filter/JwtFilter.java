
//
//@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//    private JwtUtil jwtUtil;
//    private UserRepository userRepository;
//
//    public JwtFilter(JwtUtil jwtUtil,UserRepository userRepository){
//        this.jwtUtil = jwtUtil;
//        this.userRepository = userRepository;
//    }
//
//    public JwtFilter(){}
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        final String authHeader = request.getHeader("Authorization");
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//            String username = jwtUtil.extractUsername(token);
//            if (jwtUtil.validateToken(token,username)) {
////                String username = jwtUtil.extractUsername(token);
//
//                User user = userRepository.findByUsername(username).orElse(null);
//                if (user != null) {
//                    UsernamePasswordAuthenticationToken auth =
//                            new UsernamePasswordAuthenticationToken(
//                                    user, null, List.of(new SimpleGrantedAuthority(user.getRole().name()))
//                            );
////                    SecurityContextHolder.getContext().setAuthentication(auth);
//                }
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}

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
         System.out.println(authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            // Extract info from token (assuming you decode and verify here).
            System.out.println(token);
            String userRole = jwtUtil.extractRole(token);
            httpRequest.setAttribute("userRole", userRole);
        }

        chain.doFilter(request, response);
    }
}

