package com.example.booking_service.repository;

import com.example.booking_service.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
 
import java.util.List;
 
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
}
