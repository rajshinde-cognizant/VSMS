package com.example.service_center.service;

 import com.example.service_center.dto.*;
 import com.example.service_center.entity.*;
 import com.example.service_center.repository.*;
 import lombok.RequiredArgsConstructor;
 import lombok.extern.slf4j.Slf4j;
 import org.springframework.stereotype.Service;
  
 import java.util.List;
import java.util.Optional;
  
 @Service
 @RequiredArgsConstructor
 @Slf4j
 public class ServiceCenterService {
  
     private final ServiceCenterRepository serviceCenterRepository;
     private final MechanicRepository mechanicRepository;
     private final ServiceTypeRepository serviceTypeRepository;
  
     public ServiceCenter addServiceCenter(String email, ServiceCenterRequest request) {
         log.info("Saving service center with name: {}", request.getName());
         ServiceCenter serviceCenter = ServiceCenter.builder()
                 .name(request.getName())
                 .location(request.getLocation())
                 .contact(request.getContact())
                 .build();
         return serviceCenterRepository.save(serviceCenter);
     }
  
     public List<ServiceCenter> getAllServiceCenters() {
         return serviceCenterRepository.findAll();
     }
  
     public ServiceCenter getServiceCenterById(Long id) {
         return serviceCenterRepository.findById(id).orElseThrow();
     }
  
     public Mechanic addMechanic(String email, Long serviceCenterId, MechanicRequest request) {
         ServiceCenter center = serviceCenterRepository.findById(serviceCenterId).orElseThrow();
         Mechanic mechanic = Mechanic.builder()
                 .name(request.getName())
                 .expertise(request.getExpertise())
                 .serviceCenter(center)
                 .build();
         return mechanicRepository.save(mechanic);
     }
  
     public List<Mechanic> getMechanicsByCenter(Long serviceCenterId) {
         return mechanicRepository.findByServiceCenterId(serviceCenterId);
     }
  
     public ServiceType addServiceType(String email,Long serviceCenterId, ServiceTypeRequest request) {
        ServiceCenter center = serviceCenterRepository.findById(serviceCenterId).orElseThrow();
         ServiceType type = ServiceType.builder()
                 .description(request.getDescription())
                 .price(request.getPrice())
                 .serviceCenter(center)
                 .build();
         return serviceTypeRepository.save(type);
     }
  
     public List<ServiceType> getAllServiceTypes() {
         return serviceTypeRepository.findAll();
     }

     public Optional<ServiceType> getServiceTypeById(Long id){
        return serviceTypeRepository.findById(id);
     }
 }
  