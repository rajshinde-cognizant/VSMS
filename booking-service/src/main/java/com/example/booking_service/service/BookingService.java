package com.example.booking_service.service;

import com.example.booking_service.client.UserClient;
import com.example.booking_service.client.VehicleClient;
import com.example.booking_service.client.ServiceCenterClient;
import com.example.booking_service.dto.*;
import com.example.booking_service.entity.Booking;
import com.example.booking_service.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserClient userClient;
    private final VehicleClient vehicleClient;
    private final ServiceCenterClient serviceCenterClient;

    public BookingResponse createBooking(BookingRequest request) {
        // Validate user and vehicle
        UserResponse user = userClient.getUserByEmail(request.getUserEmail());
        VehicleResponse vehicle = vehicleClient.getOne(request.getVehicleId());
        ServiceCenterResponse serviceCenter=serviceCenterClient.getServiceCenterById(request.getServiceCenterId());

        // Create booking entity
        Booking booking = Booking.builder()
                .userEmail(request.getUserEmail())
                .vehicleId(request.getVehicleId())
                .serviceCenterId(request.getServiceCenterId())
                .serviceTypeId((request.getServiceTypeId()))
                .bookingDate(request.getBookingDate())
                .status(request.getStatus())
                .build();

        // Save booking
        booking = bookingRepository.save(booking);
        log.info("Booking created with ID: {}", booking.getId());

        // Convert to response
        return toResponse(booking, user, vehicle,serviceCenter);
    }

    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(booking -> {
                    UserResponse user = userClient.getUserByEmail(booking.getUserEmail());
                    VehicleResponse vehicle = vehicleClient.getOne(booking.getVehicleId());
                    ServiceCenterResponse serviceCenter=serviceCenterClient.getServiceCenterById(booking.getServiceCenterId());
                    return toResponse(booking, user, vehicle,serviceCenter);
                })
                .collect(Collectors.toList());
    }

    public BookingResponse getBookingById(Long id) {
        return bookingRepository.findById(id)
                .map(booking -> {
                    UserResponse user = userClient.getUserByEmail(booking.getUserEmail());
                    VehicleResponse vehicle = vehicleClient.getOne(booking.getVehicleId());
                    ServiceCenterResponse serviceCenter=serviceCenterClient.getServiceCenterById(booking.getServiceCenterId());
                    return toResponse(booking, user, vehicle,serviceCenter);
                })
                .orElse(null);
    }

    public List<BookingResponse> getBookingsByUserId(String userEmail) {
        //System.out.println("Heloooo ***************************************************");
        log.info("Fetching bookings for userId: {}", userEmail); // Log the userId received from controller
        // 1. Fetch bookings from the database filtered by userId
        List<Booking> bookings = bookingRepository.findByUserEmail(userEmail);
        //System.out.println(bookings+"*********************************************************************************************");
        // 2. Map Booking entities to BookingResponse DTOs, enriching with data from other services
        return bookings.stream()
                .map(booking -> {
                    // Log the userId from the booking entity before calling user-service
                    log.info("Processing booking ID: {}, fetching user details for booking.userId: {}", booking.getId(), booking.getUserEmail());
                    UserResponse user = userClient.getUserByEmail(booking.getUserEmail());
                    VehicleResponse vehicle = vehicleClient.getOne(booking.getVehicleId());
                    ServiceCenterResponse serviceCenter = serviceCenterClient.getServiceCenterById(booking.getServiceCenterId());

                    // Convert the Booking entity and fetched details into a BookingResponse DTO
                    return toResponse(booking, user, vehicle, serviceCenter);
                })
                .collect(Collectors.toList());
    }

    public BookingResponse updateBooking(Long id, BookingRequest request) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        booking.setBookingDate(request.getBookingDate());
        booking.setStatus(request.getStatus());
        booking = bookingRepository.save(booking);

        UserResponse user = userClient.getUserByEmail(booking.getUserEmail());
        VehicleResponse vehicle = vehicleClient.getOne(booking.getVehicleId());
        ServiceCenterResponse serviceCenter=serviceCenterClient.getServiceCenterById(request.getServiceCenterId());
        return toResponse(booking, user, vehicle,serviceCenter);
    }

    public void deleteBooking(Long id) {
        log.info("Deleting booking with ID: {}", id);
        bookingRepository.deleteById(id);
    }

    private BookingResponse toResponse(Booking booking, UserResponse user, VehicleResponse vehicle, ServiceCenterResponse serviceCenter) {
        return BookingResponse.builder()
                .id(booking.getId())
                .userEmail(booking.getUserEmail()) // Include userId
                .vehicleId(booking.getVehicleId()) // Include vehicleId
                .serviceCenterId(booking.getServiceCenterId())
                .serviceTypeId((booking.getServiceTypeId()))
                .bookingDate(booking.getBookingDate())
                .status(booking.getStatus())
                .user(user)
                .vehicle(vehicle)
                .serviceCenter(serviceCenter)
                .build();
    }
}