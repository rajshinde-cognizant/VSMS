package com.example.invoice_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.invoice_service.entity.Invoice;
 
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByBookingId(Long bookingId);
}
