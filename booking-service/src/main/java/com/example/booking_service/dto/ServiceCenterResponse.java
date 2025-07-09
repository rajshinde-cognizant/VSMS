package com.example.booking_service.dto;

import lombok.*;

@Data
@Builder
public class ServiceCenterResponse {
    private String name;
    private String location;
    private String contact;

}
