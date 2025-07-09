package com.example.vehicle_service.dto;

import lombok.Data;
 
@Data
public class VehicleRequest {
    private String make;
    private String model;
    private int regYear;
    private String registrationNumber;
}