package com.example.booking_service.client;

import com.example.booking_service.config.FeignClientConfig;
import com.example.booking_service.dto.VehicleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "vehicle-service", configuration = FeignClientConfig.class)
public interface VehicleClient {
 
    @GetMapping("/api/vehicles/{id}")
    VehicleResponse getOne(@PathVariable("id") Long id);

//    @GetMapping("/api/vehicles/user/{id}")
//    List<VehicleResponse> getVehiclesByUserId(@PathVariable("id") Long userId);
}