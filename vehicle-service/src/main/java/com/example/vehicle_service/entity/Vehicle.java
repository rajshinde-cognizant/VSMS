package com.example.vehicle_service.entity;

import jakarta.persistence.*;
import lombok.*;
 
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String userEmail;
    private String make;
    private String model;
    private int regYear;
    private String registrationNumber;
}
