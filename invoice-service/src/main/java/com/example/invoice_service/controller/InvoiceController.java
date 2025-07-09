package com.example.invoice_service.controller;

import com.example.invoice_service.dto.InvoiceResponse;
import com.example.invoice_service.dto.InvoiceRequest;
import com.example.invoice_service.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
 
@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;
 
    @PostMapping
    public ResponseEntity<InvoiceResponse> generateInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        return ResponseEntity.ok(invoiceService.generateInvoice(invoiceRequest.getBookingId(), invoiceRequest.getServiceTypeId()));
    }
 
    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> getInvoiceById(@PathVariable Long id) {
        InvoiceResponse response = invoiceService.getInvoice(id);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }
 
    @PutMapping("/{id}/status")
    public ResponseEntity<InvoiceResponse> updatePaymentStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(invoiceService.updatePaymentStatus(id, status));
    }
}