package com.example.service_center.entity;

import jakarta.persistence.*;
import lombok.*;
 
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="service_type")
public class ServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private double price;
 
    @ManyToOne
    private ServiceCenter serviceCenter;
}