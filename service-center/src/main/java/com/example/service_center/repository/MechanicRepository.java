package com.example.service_center.repository;

import com.example.service_center.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
 
public interface MechanicRepository extends JpaRepository<Mechanic, Long> {
    List<Mechanic> findByServiceCenterId(Long centerId);
}

