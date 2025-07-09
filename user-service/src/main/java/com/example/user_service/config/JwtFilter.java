package com.example.user_service.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

        public JwtFilter(JwtUtil jwtUtil) {
            this.jwtUtil = jwtUtil;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                        FilterChain filterChain) throws ServletException, IOException {

            String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            String email = jwtUtil.extractEmail(token);
            String role = jwtUtil.extractRole(token);
 
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
            Authentication auth = new UsernamePasswordAuthenticationToken(email, null, List.of(authority));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
            filterChain.doFilter(request, response);
        }



 
    // private final JwtUtil jwtUtil;
 
    // public JwtFilter(JwtUtil jwtUtil) {
    //     this.jwtUtil = jwtUtil;
    // }
 
    // @Override
    // protected void doFilterInternal(HttpServletRequest request,
    //                                 HttpServletResponse response,
    //                                 FilterChain chain) throws ServletException, IOException {
 
    //     String header = request.getHeader("Authorization");
    //     if (header != null && header.startsWith("Bearer ")) {
    //         String token = header.substring(7);
    //         String email = jwtUtil.extractEmail(token);
    //         List<String> roles = jwtUtil.extractRole(token);
 
    //         // SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
    //         List authority = roles.stream()
    //         .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // This line is crucial!
    //         .toList();
    //         Authentication auth = new UsernamePasswordAuthenticationToken(email, null, authority);
    //         SecurityContextHolder.getContext().setAuthentication(auth);
    //     }
    //     chain.doFilter(request, response);
    // }
}
