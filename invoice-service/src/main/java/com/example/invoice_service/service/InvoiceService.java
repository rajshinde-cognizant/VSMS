package com.example.invoice_service.service;

import com.example.invoice_service.client.*;
import com.example.invoice_service.dto.UserResponse;
import com.example.invoice_service.dto.*;
import com.example.invoice_service.entity.Invoice;
import com.example.invoice_service.repository.InvoiceRepository;
import com.itextpdf.text.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring6.SpringTemplateEngine;
import com.itextpdf.text.pdf.PdfWriter;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceService {
        private final InvoiceRepository invoiceRepository;
        private final BookingClient bookingClient;
        private final ServiceTypeClient serviceTypeClient;
        private final TemplateEngine templateEngine;
        
        private final UserClient userClient;
        private final VehicleClient vehicleClient;
        public InvoiceResponse generateInvoice(Long bookingId, Long serviceTypeId) {
                BookingResponse booking = bookingClient.getBookingById(bookingId);
                ServiceTypeResponse serviceType = serviceTypeClient.getServiceTypeById(serviceTypeId);
                double amount = serviceType.getPrice();

                Invoice invoice = Invoice.builder()
                                .bookingId(bookingId)
                                .serviceTypeId(serviceTypeId)
                                .userEmail(booking.getUserEmail())
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

        public List<InvoiceResponse> getInvoicesByUserEmail(String userEmail) {
                log.info("Fetching invoices for user email: {}", userEmail);
                return invoiceRepository.findByUserEmail(userEmail).stream()
                        .map(invoice -> toResponse(invoice,
                                bookingClient.getBookingById(invoice.getBookingId()),
                                serviceTypeClient.getServiceTypeById(invoice.getServiceTypeId())))
                        .collect(Collectors.toList());
        }

        // Helper method for @PreAuthorize
        public boolean isInvoiceOwnedBy(Long invoiceId, String userEmail) {
                return invoiceRepository.findById(invoiceId)
                        .map(invoice -> invoice.getUserEmail().equals(userEmail))
                        .orElse(false);
        }

//        // NEW METHOD: Generate Invoice PDF
//        public byte[] generateInvoicePdf(Long invoiceId) throws Exception {
//                Invoice invoice = invoiceRepository.findById(invoiceId)
//                        .orElseThrow(() -> new RuntimeException("Invoice not found with ID: " + invoiceId));
//
//                // Fetch all related details needed for the invoice content
//                BookingResponse booking = bookingClient.getBookingById(invoice.getBookingId());
//                ServiceTypeResponse serviceType = serviceTypeClient.getServiceTypeById(invoice.getServiceTypeId());
//                UserResponse user = userClient.getUserByEmail(booking.getUserEmail());
//                VehicleResponse vehicle = vehicleClient.getOne(booking.getVehicleId());
//                ServiceCenterResponse serviceCenter = serviceTypeClient.getServiceCenterById(booking.getServiceCenterId());
//
//                // Create PDF using iText
//                Document document = new Document();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                PdfWriter.getInstance(document, baos);
//                document.open();
//
//                // Define fonts
//                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.BLACK);
//                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.DARK_GRAY);
//                Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
//
//                // Add content to the PDF
//                document.add(new Paragraph("Invoice Details", titleFont));
//                document.add(new Paragraph("\n")); // Add some space
//
//                document.add(new Paragraph("Invoice ID: " + invoice.getId(), headerFont));
//                document.add(new Paragraph("Payment Status: " + invoice.getPaymentStatus(), normalFont));
//                document.add(new Paragraph("\n"));
//
//                document.add(new Paragraph("User Information:", headerFont));
//                document.add(new Paragraph("Name: " + (user != null ? user.getName() : "N/A"), normalFont));
//                document.add(new Paragraph("Email: " + (user != null ? user.getEmail() : "N/A"), normalFont));
//                document.add(new Paragraph("\n"));
//
//                document.add(new Paragraph("Booking Details:", headerFont));
//                document.add(new Paragraph("Booking ID: " + (booking != null ? booking.getId() : "N/A"), normalFont));
//                document.add(new Paragraph("Booking Date: " + (booking != null ? booking.getBookingDate() : "N/A"), normalFont));
//                document.add(new Paragraph("Booking Status: " + (booking != null ? booking.getStatus() : "N/A"), normalFont));
//                document.add(new Paragraph("\n"));
//
//                document.add(new Paragraph("Vehicle Information:", headerFont));
//                document.add(new Paragraph("Model: " + (vehicle != null ? vehicle.getModel() : "N/A"), normalFont));
//                document.add(new Paragraph("Registration Number: " + (vehicle != null ? vehicle.getRegistrationNumber() : "N/A"), normalFont));
//                document.add(new Paragraph("\n"));
//
//                document.add(new Paragraph("Service Information:", headerFont));
//                document.add(new Paragraph("Service Type: " + (serviceType != null ? serviceType.getDescription() : "N/A"), normalFont));
//                document.add(new Paragraph("Service Center: " + (serviceCenter != null ? serviceCenter.getName() : "N/A"), normalFont));
//                document.add(new Paragraph("\n"));
//
//                document.add(new Paragraph("Total Amount: $" + String.format("%.2f", invoice.getTotalAmount()), titleFont));
//
//                document.close();
//                return baos.toByteArray();
//        }

        // NEW METHOD: Generate Invoice PDF using Thymeleaf and OpenHTMLToPDF
        public byte[] generateInvoicePdf(Long invoiceId) throws Exception {
                Invoice invoiceEntity = invoiceRepository.findById(invoiceId)
                        .orElseThrow(() -> new RuntimeException("Invoice not found with ID: " + invoiceId));

                // Fetch all related details needed for the invoice content
                BookingResponse booking = bookingClient.getBookingById(invoiceEntity.getBookingId());
                ServiceTypeResponse serviceType = serviceTypeClient.getServiceTypeById(invoiceEntity.getServiceTypeId());
                UserResponse user = userClient.getUserByEmail(booking.getUserEmail());
                VehicleResponse vehicle = vehicleClient.getOne(booking.getVehicleId());
                ServiceCenterResponse serviceCenter = serviceTypeClient.getServiceCenterById(booking.getServiceCenterId());

                // Create a full InvoiceResponse object to pass to the template
                InvoiceResponse invoiceResponse = toResponse(invoiceEntity, booking, serviceType);

                // 1. Prepare Thymeleaf context
                Context context = new Context();
                context.setVariable("invoice", invoiceResponse); // Pass the full invoiceResponse object to the template

                // 2. Process Thymeleaf template to generate HTML
                String htmlContent = templateEngine.process("invoice", context); // "invoice" refers to invoice.html

                // 3. Convert HTML to PDF using OpenHTMLToPDF
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.withHtmlContent(htmlContent, null); // Base URL can be null for simple cases
                builder.toStream(baos);
                builder.run();

                return baos.toByteArray();
        }


        private InvoiceResponse toResponse(Invoice invoice, BookingResponse booking, ServiceTypeResponse serviceType) {

                UserResponse user = userClient.getUserByEmail(booking.getUserEmail());
                VehicleResponse vehicle = vehicleClient.getOne(booking.getVehicleId());
                ServiceCenterResponse service= serviceTypeClient.getServiceCenterById(booking.getServiceCenterId());
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
