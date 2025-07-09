package com.example.invoice_service.dto;
 
public class InvoiceRequest {
    private Long bookingId;
    private Long serviceTypeId;

    public InvoiceRequest() {
    }

    public InvoiceRequest(Long bookingId, Long serviceTypeId) {
        this.bookingId = bookingId;
        this.serviceTypeId = serviceTypeId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Long serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }
}