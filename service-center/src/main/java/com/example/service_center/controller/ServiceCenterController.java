package com.example.service_center.controller;
 
import com.example.service_center.config.JwtUtil;
import com.example.service_center.dto.MechanicRequest;
import com.example.service_center.dto.ServiceCenterRequest;
import com.example.service_center.dto.ServiceTypeRequest;
import com.example.service_center.entity.Mechanic;
import com.example.service_center.entity.ServiceCenter;
import com.example.service_center.entity.ServiceType;
import com.example.service_center.service.ServiceCenterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
 
@RestController
@RequestMapping("/api/service-centers")
@RequiredArgsConstructor
@Slf4j
public class ServiceCenterController {
    
    @Autowired
    private ServiceCenterService serviceCenterService;

    @Autowired
    private JwtUtil jwtUtil;
 
    private String extractEmail(String authHeader) {
        return jwtUtil.extractEmail(authHeader.substring(7));
    }
 
    @PostMapping
    public ResponseEntity<ServiceCenter> addServiceCenter(@RequestHeader("Authorization") String authHeader,
                                                          @RequestBody ServiceCenterRequest request) {
        log.info("Adding new service center");
        String email = extractEmail(authHeader);
        return ResponseEntity.ok(serviceCenterService.addServiceCenter(email, request));
    }
 
    @GetMapping
    public ResponseEntity<List<ServiceCenter>> getAllServiceCenters() {
        log.info("Fetching all service centers");
        return ResponseEntity.ok(serviceCenterService.getAllServiceCenters());
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<ServiceCenter> getServiceCenterById(@PathVariable Long id) {
        log.info("Fetching service center with ID: {}", id);
        return ResponseEntity.ok(serviceCenterService.getServiceCenterById(id));
    }
 
    @PostMapping("/{id}/mechanics")
    public ResponseEntity<Mechanic> addMechanic(@RequestHeader("Authorization") String authHeader,
                                                @PathVariable Long id,
                                                @RequestBody MechanicRequest request) {
        String email = extractEmail(authHeader);
        log.info("Adding mechanic to service center ID: {}", id);
        return ResponseEntity.ok(serviceCenterService.addMechanic(email, id, request));
    }
 
    @GetMapping("/{id}/mechanics")
    public ResponseEntity<List<Mechanic>> getMechanicsByCenter(@PathVariable Long id) {
        log.info("Fetching mechanics for service center ID: {}", id);
        return ResponseEntity.ok(serviceCenterService.getMechanicsByCenter(id));
    }
 
    @PostMapping("{id}/service-types")
    public ResponseEntity<ServiceType> addServiceType(@RequestHeader("Authorization") String authHeader, @PathVariable Long id,
                                                      @RequestBody ServiceTypeRequest request) {
        log.info("Adding service type");
        String email = extractEmail(authHeader);
        return ResponseEntity.ok(serviceCenterService.addServiceType(email,id, request));
    }
 
    @GetMapping("/service-types")
    public ResponseEntity<List<ServiceType>> getAllServiceTypes() {
        log.info("Fetching all service types");
        return ResponseEntity.ok(serviceCenterService.getAllServiceTypes());
    }

    @GetMapping("/service-types/{id}")
    public ResponseEntity<ServiceType> getServiceTypeById(@PathVariable Long id) {
        Optional<ServiceType> serviceType = serviceCenterService.getServiceTypeById(id);
        return serviceType.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }
    

}