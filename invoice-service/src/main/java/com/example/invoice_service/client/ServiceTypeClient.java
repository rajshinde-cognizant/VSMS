package com.example.invoice_service.client;

import com.example.invoice_service.dto.ServiceCenterResponse;
import com.example.invoice_service.dto.ServiceTypeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
 
@FeignClient(name = "service-center")
public interface ServiceTypeClient {
    @GetMapping("/api/service-centers/{id}")
    ServiceCenterResponse getServiceCenterById(@PathVariable("id") Long id);

    @GetMapping("/api/service-centers/service-types/{id}")
    ServiceTypeResponse getServiceTypeById(@PathVariable Long id);
}


