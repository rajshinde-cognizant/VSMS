package com.example.invoice_service.client;

import com.example.invoice_service.config.FeignClientConfig;
import com.example.invoice_service.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
 
@FeignClient(name = "user-service", configuration = FeignClientConfig.class)
public interface UserClient {
 
    @GetMapping("/api/users/{id}")
    UserResponse getUserById(@PathVariable("id") Long id);
}
