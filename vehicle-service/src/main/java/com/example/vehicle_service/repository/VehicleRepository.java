package com.example.vehicle_service.repository;

import com.example.vehicle_service.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
 
import java.util.List;
 
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByUserEmail(String email);
//    List<Vehicle> findByUserId(Long id);
}
