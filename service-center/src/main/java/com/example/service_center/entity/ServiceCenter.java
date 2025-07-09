package com.example.service_center.entity;
import jakarta.persistence.*;
import lombok.*;
 
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="service_center")
public class ServiceCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private String contact;
}
