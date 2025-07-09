package com.example.invoice_service.service;

import com.example.invoice_service.client.*;
import com.example.invoice_service.dto.UserResponse;
import com.example.invoice_service.dto.*;
import com.example.invoice_service.entity.Invoice;
import com.example.invoice_service.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceService {
        private final InvoiceRepository invoiceRepository;
        private final BookingClient bookingClient;
        private final ServiceTypeClient serviceTypeClient;
        
        private final UserClient userClient;
        private final VehicleClient vehicleClient;


        public InvoiceResponse generateInvoice(Long bookingId, Long serviceTypeId) {
                BookingResponse booking = bookingClient.getBookingById(bookingId);
                ServiceTypeResponse serviceType = serviceTypeClient.getServiceTypeById(serviceTypeId);
                double amount = serviceType.getPrice();

                Invoice invoice = Invoice.builder()
                                .bookingId(bookingId)
                                .serviceTypeId(serviceTypeId)
                                .totalAmount(amount)
                                .paymentStatus("UNPAID")
                                .build();
                invoice = invoiceRepository.save(invoice);
                log.info("Invoice generated with ID: {}", invoice.getId());

                return toResponse(invoice, booking, serviceType);
        }

        public List<InvoiceResponse> getAllInvoices() {
                return invoiceRepository.findAll().stream()
                                .map(invoice -> toResponse(invoice,
                                                bookingClient.getBookingById(invoice.getBookingId()),
                                                serviceTypeClient.getServiceTypeById(invoice.getServiceTypeId())))
                                .collect(Collectors.toList());
        }

        public InvoiceResponse getInvoice(Long id) {
                return invoiceRepository.findById(id)
                                .map(invoice -> toResponse(invoice,
                                                bookingClient.getBookingById(invoice.getBookingId()),
                                                serviceTypeClient.getServiceTypeById(invoice.getServiceTypeId())))
                                .orElse(null);
        }

        public InvoiceResponse updatePaymentStatus(Long id, String status) {
                Invoice invoice = invoiceRepository.findById(id).orElseThrow();
                invoice.setPaymentStatus(status);
                invoice = invoiceRepository.save(invoice);
                log.info("Payment status updated for Invoice ID: {}", invoice.getId());
                return toResponse(invoice,
                                bookingClient.getBookingById(invoice.getBookingId()),
                                serviceTypeClient.getServiceTypeById(invoice.getServiceTypeId()));
        }

        private InvoiceResponse toResponse(Invoice invoice, BookingResponse booking, ServiceTypeResponse serviceType) {

                UserResponse user = userClient.getUserById(booking.getUserId());
                VehicleResponse vehicle = vehicleClient.getOne(booking.getVehicleId());
                return InvoiceResponse.builder()
                                .invoiceId(invoice.getId())
                                .booking(booking)
                                .userResponse(user)
                                .vehicleResponse(vehicle)
                                .serviceType(serviceType)
                                .totalAmount(invoice.getTotalAmount())
                                .paymentStatus(invoice.getPaymentStatus())
                                .build();
        }
}
