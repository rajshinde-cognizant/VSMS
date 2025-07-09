package com.example.invoice_service.client;

import com.example.invoice_service.config.FeignClientConfig;
import com.example.invoice_service.dto.VehicleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
 
@FeignClient(name = "vehicle-service", configuration = FeignClientConfig.class)
public interface VehicleClient {
 
    @GetMapping("/api/vehicles/{id}")
    VehicleResponse getOne(@PathVariable("id") Long id);
}