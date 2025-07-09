package com.example.vehicle_service.dto;

import lombok.*;
 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponse {
    private Long id;
    private String make;
    private String model;
    private int regYear;
    private String registrationNumber;
}