package com.example.service_center.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.service_center.entity.ServiceCenter;
 
public interface ServiceCenterRepository extends JpaRepository<ServiceCenter, Long> {}