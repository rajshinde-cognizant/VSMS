package com.example.booking_service.dto;

import lombok.*;
import java.time.LocalDate;
 
@Data
public class BookingRequest {
    private Long userId;
    private Long vehicleId;
    private Long serviceCenterId;
    private LocalDate bookingDate;
    private String status;
}