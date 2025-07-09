package com.example.booking_service.client;

import com.example.booking_service.config.FeignClientConfig;
import com.example.booking_service.dto.ServiceCenterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "service-center", configuration = FeignClientConfig.class)
public interface ServiceCenterClient {
    
    @GetMapping("/api/service-centers/{id}")
    ServiceCenterResponse getServiceCenterById(@PathVariable("id") Long id);
}
