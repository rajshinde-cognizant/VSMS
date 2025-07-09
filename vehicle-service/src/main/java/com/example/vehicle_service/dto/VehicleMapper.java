package com.example.vehicle_service.dto;

import com.example.vehicle_service.entity.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
 
@Slf4j
@Component
public class VehicleMapper {
 
    public Vehicle toEntity(VehicleRequest dto, String email) {
        log.debug("Mapping VehicleRequest to Vehicle for user: {}", email);
        Vehicle v = new Vehicle();
        v.setUserEmail(email);
        v.setMake(dto.getMake());
        v.setModel(dto.getModel());
        v.setRegYear(dto.getRegYear());
        v.setRegistrationNumber(dto.getRegistrationNumber());
        return v;
    }
 
    public VehicleResponse toDto(Vehicle v) {
        return new VehicleResponse(
            v.getId(), v.getMake(), v.getModel(), v.getRegYear(), v.getRegistrationNumber()
        );
    }
 
    public void updateEntity(Vehicle v, VehicleRequest dto) {
        v.setMake(dto.getMake());
        v.setModel(dto.getModel());
        v.setRegYear(dto.getRegYear());
        v.setRegistrationNumber(dto.getRegistrationNumber());
    }
}
