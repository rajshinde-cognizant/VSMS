package com.example.booking_service.client;

import com.example.booking_service.config.FeignClientConfig;
import com.example.booking_service.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "user-service", configuration = FeignClientConfig.class)
public interface UserClient {
 
    @GetMapping("/api/users/{id}")
    UserResponse getUserById(@PathVariable("id") Long id);

    @GetMapping("/api/users")
    UserResponse getUserByEmail(@RequestParam("email") String email);

    // If you need all users (e.g., for admin features within booking service)
    @GetMapping("/api/users/all")
    List<UserResponse> getAllUsers();
}
