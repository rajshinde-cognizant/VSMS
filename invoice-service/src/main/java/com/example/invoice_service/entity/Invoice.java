package com.example.invoice_service.entity;
 
import jakarta.persistence.*;
import lombok.*;
 
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookingId;
    private Long serviceTypeId;
    private Double totalAmount;
    private String paymentStatus;
}
