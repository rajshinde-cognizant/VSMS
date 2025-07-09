package com.example.auth_service.controller;
 
import com.example.auth_service.dto.AuthRequest;
import com.example.auth_service.dto.JwtResponse;
import com.example.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
 
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;
 
    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody AuthRequest request,
                                                 @RequestParam String role) {
        return ResponseEntity.ok(authService.register(request, role));
    }
 
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
 


// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import com.example.auth_service.config.JwtUtil;
// import com.example.auth_service.entity.User;
// import com.example.auth_service.repository.UserRepository;
// import com.example.auth_service.dto.AuthRequest;
// import com.example.auth_service.dto.RegisterRequest;
// import com.example.auth_service.dto.JwtResponse;
// import com.example.auth_service.service.AuthService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;

// @RestController
// @RequestMapping("/api/auth")
// @RequiredArgsConstructor
// public class AuthController {
 
//     private final AuthService authService;
 
//     @PostMapping("/register")
//     public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest request) {
//         String token = authService.register(request);
//         return ResponseEntity.ok(new JwtResponse(token));
//     }
 
//     @PostMapping("/login")
//     public ResponseEntity<JwtResponse> login(@RequestBody AuthRequest request) {
//         String token = authService.login(request);
//         return ResponseEntity.ok(new JwtResponse(token));
//     }
 
//     @GetMapping("/validate")
//     public ResponseEntity<String> validate(@RequestHeader("Authorization") String authHeader) {
//         System.out.println("\n\nAuthorization Header: " + authHeader+"\n\n");
//         String token = authHeader.substring(7); // Remove "Bearer "
//         if (authService.validateToken(token)) {
//             return ResponseEntity.ok("Valid Token");
//         } else {
//             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
//         }
//     }
// }