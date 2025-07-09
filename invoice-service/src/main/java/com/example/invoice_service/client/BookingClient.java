package com.example.invoice_service.client;

import com.example.invoice_service.dto.BookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
 
@FeignClient(name = "booking-service")
public interface BookingClient {
    @GetMapping("/api/bookings/{id}")
    BookingResponse getBookingById(@PathVariable Long id);
}