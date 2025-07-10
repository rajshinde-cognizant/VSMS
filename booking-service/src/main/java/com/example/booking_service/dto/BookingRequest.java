package com.example.booking_service.dto;

import lombok.*;
import java.time.LocalDate;
 
@Data
public class BookingRequest {
    private String userEmail;
    private Long vehicleId;
    private Long serviceCenterId;
    private Long serviceTypeId;
    private LocalDate bookingDate;
    private String status;
}