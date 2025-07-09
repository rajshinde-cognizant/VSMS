package com.example.invoice_service.dto;

import java.time.LocalDate;

import lombok.*;

@Data
public class BookingResponse {
    private Long id;
    private Long userId;
    private Long vehicleId;
    // ServiceCenterResponse serviceCenterResponse;
    // private Long serviceCenterId;
    private LocalDate bookingDate;
    // private String timeSlot;
    private String status;
}
