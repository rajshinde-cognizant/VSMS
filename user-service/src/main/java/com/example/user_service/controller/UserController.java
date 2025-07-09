package com.example.user_service.controller;

import com.example.user_service.entity.User;
import com.example.user_service.service.UserService;
import com.example.user_service.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // Helper to extract email from JWT
    private String extractEmail(String authHeader) {
        String token = authHeader.substring(7); // remove "Bearer "
        return jwtUtil.extractEmail(token);
    }

    // ✅ Get current user's profile
    @GetMapping("/me")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<User> getMyProfile(@RequestHeader("Authorization") String authHeader) {
        String email = extractEmail(authHeader);
        return ResponseEntity.ok(userService.getOrCreateUserByEmail(email));
    }

    // ✅ Update current user's profile
    @PostMapping("/me")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<User> updateMyProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody User updatedUser
    ) {
        String email = extractEmail(authHeader);
        return ResponseEntity.ok(userService.updateUserByEmail(email, updatedUser));
    }

    // ✅ Admin: View all users
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    // ✅ Admin: Get any user by email
    @GetMapping
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    // ✅ Optional: Add user manually (e.g. from Auth Service via Feign)
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createIfNotExists(user.getEmail()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}


//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//
//import com.example.user_service.dto.UserRequest;
//import com.example.user_service.entity.User;
//import com.example.user_service.service.UserService;
//import com.example.user_service.config.JwtUtil;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PathVariable;
//import java.util.Optional;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/users")
//@RequiredArgsConstructor
//public class UserController {
//
//    private final UserService userService;
//
////    @PostMapping("/me")
////    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN')")
////    public ResponseEntity<?> create(@RequestHeader("Authorization") String authHeader,
////                                                 @RequestBody UserRequest request) {
////        String email = extractEmail(authHeader);
////        return ResponseEntity.ok(userService.create(email, request));
////    }
//
//    @PostMapping("/me")
//    @PreAuthorize("hasAuthority('CUSTOMER')")
//    public ResponseEntity<?> update(@RequestHeader("Authorization") String authHeader,
//                                    @RequestBody UserRequest request) {
//        String email = extractEmail(authHeader);
//        return ResponseEntity.ok(userService.update(email, request));
//    }
//
//    @GetMapping("/me")
//    @PreAuthorize("hasAuthority('CUSTOMER')")
//    public ResponseEntity<?> getMyProfile(@RequestHeader("Authorization") String authHeader) {
//        String email = extractEmail(authHeader);
//        System.out.println(email);
//        return ResponseEntity.ok(userService.getByEmail(email));
//    }
//
//    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<List<User>> getAllUsers() {
//        return ResponseEntity.ok(userService.getAll());
//    }
//
//    private String extractEmail(String authHeader) {
//        String token = authHeader.substring(7);
//        return new JwtUtil().extractEmail(token);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable Long id) {
//        Optional<User> user = userService.getById(id);
//        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//}

