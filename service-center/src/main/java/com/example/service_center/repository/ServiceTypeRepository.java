package com.example.service_center.repository;

import com.example.service_center.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
    List<ServiceType> findByServiceCenterId(Long centerId);
}

