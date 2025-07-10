package com.example.booking_service.repository;

import com.example.booking_service.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
 
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findById(Long id);
    List<Booking> findByUserEmail(String userEmail);
}
