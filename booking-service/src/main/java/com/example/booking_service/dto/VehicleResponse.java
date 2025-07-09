package com.example.booking_service.dto;

import lombok.Data;
 
@Data
public class VehicleResponse {
    private Long id;
    private String make;
    private String model;
    private int regYear;
    private String registrationNumber;
}