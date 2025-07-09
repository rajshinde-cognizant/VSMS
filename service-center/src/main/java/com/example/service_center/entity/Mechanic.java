package com.example.service_center.entity;

import jakarta.persistence.*;
import lombok.*;
 

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "mechanic")
@Entity
public class Mechanic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String expertise;
 
    @ManyToOne
    private ServiceCenter serviceCenter;
}