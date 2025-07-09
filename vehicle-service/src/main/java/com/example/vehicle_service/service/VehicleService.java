package com.example.vehicle_service.service;

import com.example.vehicle_service.dto.*;
import com.example.vehicle_service.entity.Vehicle;
import com.example.vehicle_service.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
 
import java.util.List;
 
@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleService {
 
    private final VehicleRepository repo;
    private final VehicleMapper mapper;
 
    public VehicleResponse save(VehicleRequest dto, String email) {
        Vehicle v = mapper.toEntity(dto, email);
return mapper.toDto(repo.save(v));
    }
 
    public List<VehicleResponse> getByUser(String email) {
        return repo.findByUserEmail(email).stream().map(mapper::toDto).toList();
    }
 
    public VehicleResponse getById(Long id) {
        return mapper.toDto(getVehicle(id));
    }
 
    public VehicleResponse update(Long id, VehicleRequest dto) {
        Vehicle existing = getVehicle(id);
        mapper.updateEntity(existing, dto);
return mapper.toDto(repo.save(existing));
    }
 
    public void delete(Long id) {
        repo.deleteById(id);
    }
 
    private Vehicle getVehicle(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }

//    public List<Vehicle> getVehiclesByUserId(Long userId) {
//        return repo.findByUserId(userId);
//    }

}
