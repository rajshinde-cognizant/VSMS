package com.example.vehicle_service.controller;

import com.example.vehicle_service.dto.*;
import com.example.vehicle_service.entity.Vehicle;
import com.example.vehicle_service.service.VehicleService;
import com.example.vehicle_service.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.beans.factory.annotation.Autowired;
 
import java.util.List;
 
@Slf4j
@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {
 
    @Autowired
    private VehicleService service;

    @Autowired
    private JwtUtil jwtUtil;
 
    private String extractEmail(String authHeader) {
        return jwtUtil.extractEmail(authHeader.substring(7));
    }
 
    @PostMapping
    public ResponseEntity<VehicleResponse> add(@RequestBody VehicleRequest dto,
                                            @RequestHeader("Authorization") String auth) {
        log.info("Received Payload: {}", dto);
        String email = extractEmail(auth);
        log.info("Adding vehicle for: {}", email);
        return ResponseEntity.ok(service.save(dto, email));
    }
 
    @GetMapping
    @PreAuthorize("hasAnyAuthority('CUSTOMER','ADMIN')")
    public ResponseEntity<List<VehicleResponse>> getAll(@RequestHeader("Authorization") String auth) {
        String email = extractEmail(auth);
        return ResponseEntity.ok(service.getByUser(email));
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> update(@PathVariable Long id,
                                                  @RequestBody VehicleRequest dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/user/{id}")
//    public ResponseEntity<List<Vehicle>> getVehiclesByUserId(@PathVariable Long userId) {
//        List<Vehicle> vehicles = service.getVehiclesByUserId(userId);
//        return ResponseEntity.ok(vehicles);
//    }
}
