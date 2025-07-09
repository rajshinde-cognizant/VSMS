package com.example.invoice_service.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponse {

    private Long id;
    private String model;
    private String registrationNumber;

}
