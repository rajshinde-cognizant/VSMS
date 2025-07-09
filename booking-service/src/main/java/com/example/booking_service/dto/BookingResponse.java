package com.example.booking_service.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
 
@Data
@Builder
public class BookingResponse {
    private Long id;
    private Long userId;
    private Long vehicleId;
    private Long serviceCenterId;
    private LocalDate bookingDate;
    private String status;
    private UserResponse user; // Add this field
    private VehicleResponse vehicle; // Add this field
    private ServiceCenterResponse serviceCenter;
}
