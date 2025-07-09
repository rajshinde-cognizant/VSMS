package com.example.auth_service.service;

import com.example.auth_service.dto.*;
import com.example.auth_service.entity.User;
import com.example.auth_service.repository.UserRepository;
import com.example.auth_service.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
 
@Service
@RequiredArgsConstructor
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;
 
    public JwtResponse register(AuthRequest request, String role) {
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(User.Role.valueOf(role.toUpperCase()))
                .build();
            userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new JwtResponse(token);
    }
 
    public JwtResponse authenticate(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new JwtResponse(token);
    }
}





// import com.example.auth_service.config.JwtUtil;
// import com.example.auth_service.dto.AuthRequest;
// import com.example.auth_service.dto.RegisterRequest;
// import com.example.auth_service.entity.User;
// import com.example.auth_service.repository.UserRepository;
// import lombok.RequiredArgsConstructor;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;
// // import org.springframework.transaction.annotation.Transactional;
// // import java.util.Optional;
// // import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// // import org.springframework.security.core.Authentication;
// // import org.springframework.security.core.context.SecurityContextHolder;
// // import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// // import com.example.auth_service.dto.JwtResponse;

// @Service
// @RequiredArgsConstructor
// public class Auth_Service {
 
//     private final UserRepository userRepository;
//     private final PasswordEncoder passwordEncoder;
//     private final JwtUtil jwtUtil;
 
//     public String register(AuthRequest request) {
//         if (userRepository.findByEmail(request.getEmail()).isPresent()) {
//             throw new RuntimeException("Email already exists");
//         }
 
//         User user = new User();
//         user.setEmail(request.getEmail());
//         user.setPassword(passwordEncoder.encode(request.getPassword()));
//         user.setRole(request.getRole());
 
//         userRepository.save(user);
 
//         return jwtUtil.generateToken(user.getEmail(), user.getRole());
//     }
 
//     public String login(AuthRequest request) {
//         User user = userRepository.findByEmail(request.getEmail())
//                 .orElseThrow(() -> new UsernameNotFoundException("Email not registered"));
 
//         if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//             throw new BadCredentialsException("Invalid password");
//         }
 
//         return jwtUtil.generateToken(user.getEmail(), user.getRole());
//     }

    
// }