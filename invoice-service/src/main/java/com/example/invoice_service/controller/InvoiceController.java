package com.example.invoice_service.controller;

import com.example.invoice_service.dto.InvoiceResponse;
import com.example.invoice_service.dto.InvoiceRequest;
import com.example.invoice_service.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
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

    @GetMapping("/user/search")
    public ResponseEntity<List<InvoiceResponse>> getInvoicesByUserEmail(@RequestParam("email") String userEmail) {
        return ResponseEntity.ok(invoiceService.getInvoicesByUserEmail(userEmail));
    }

    // NEW ENDPOINT: Download Invoice PDF
    @GetMapping("/{id}/download")
    // Secure this endpoint: Only ADMIN or the owner of the invoice can download
    @PreAuthorize("hasAuthority('ADMIN') or (hasAuthority('CUSTOMER') and @invoiceService.isInvoiceOwnedBy(#id, authentication.principal.username))")
    public ResponseEntity<byte[]> downloadInvoicePdf(@PathVariable Long id, Principal principal) {
        try {
            // Get the invoice details to check ownership if CUSTOMER
            // This check is primarily for the security context and isInvoiceOwnedBy
            // The service method will throw if not found.
            invoiceService.getInvoice(id); // Just to trigger the ownership check via @PreAuthorize

            byte[] pdfBytes = invoiceService.generateInvoicePdf(id); // Call service to generate/fetch PDF

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String filename = "invoice_" + id + ".pdf";
            headers.setContentDispositionFormData("attachment", filename); // "attachment" prompts download
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            //log.error("Error generating or downloading invoice PDF for ID {}: {}", id, e.getMessage(), e);
            // More specific error handling could be added (e.g., 404 if not found)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}