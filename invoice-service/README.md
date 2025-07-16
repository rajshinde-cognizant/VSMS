# Invoice and Billing Service

## Contributor
- Rajvardhan Shinde

## 📚 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Folder Structure](#folder-structure)
- [REST API Endpoints](#rest-api-endpoints)
- [Data Model](#data-model)
- [Module Architecture Diagram](#module-architecture-diagram)
- [Component Diagram](#component-diagram)
- [Sequence Diagram](#sequence-diagram)
- [Swagger Documentation](#swagger-documentation)
- [Run Locally](#run-locally)

---

## Overview
The Invoice and Billing Service is a Spring Boot microservice within the Vehicle Management System. It handles invoice generation, payment tracking, and PDF downloads. It communicates with other services like Booking and Service Center via Feign Clients and is registered with Eureka for service discovery.

---

## Features

- Generate invoices based on bookings and service types
- Track payment status
- Download invoice as PDF
- Integrate with Booking and Service Center services
- Registered with Eureka Discovery
- Routed via API Gateway

---

## Folder Structure
```plaintext
src/
└── main/
    ├── java/
    │   └── com.vehicle.invoice/
    │       ├── client/            # Feign clients for inter-service communication
    │       ├── config/            # Configuration classes (e.g., Feign, Swagger)
    │       ├── controller/        # REST controllers
    │       ├── dto/               # Data Transfer Objects
    │       ├── entity/            # JPA Entities
    │       ├── repository/        # Spring Data Repositories
    │       └── service/           # Business logic layer
    └── resources/
        └── application.properties  # App configuration
```

---

## REST API Endpoints

| Method | Endpoint                                 | Description                          |
|--------|------------------------------------------|--------------------------------------|
| POST   | `/api/invoices`                          | Generate invoice for a booking       |
| GET    | `/api/invoices`                          | Get all invoices for a user          |
| GET    | `/api/invoices/{id}`                     | Get invoice details                  |
| PUT    | `/api/invoices/{id}/status`              | Update payment status                |
| GET    | `/api/invoices/{id}/download`            | Download invoice PDF                 |

---

## Data Model

### Invoice Entity

| Field Name       | Data Type       | Description                              |
|------------------|------------------|------------------------------------------|
| `invoiceId`      | INT              | Primary Key, unique identifier           |
| `bookingId`      | INT              | Foreign Key referencing `BookingID`      |
| `serviceTypeId`  | INT              | Foreign Key referencing `ServiceTypeID`  |
| `totalAmount`    | DECIMAL(10,2)    | Total amount charged                     |
| `paymentStatus`  | VARCHAR(20)      | Status of the payment (e.g., Paid)       |

---

## Module Architecture Diagram

```mermaid
flowchart LR
  A[/api/invoices/] --> B[InvoiceController]
  B --> C[InvoiceService]
  C --> D[InvoiceRepository]
  D --> E[(invoice_db<br>MySQL Database)]
  C --> F[FeignClient: BookingService]
  C --> G[FeignClient: ServiceCenterService]

  %% Color Scheme Styling
  classDef endpoint fill:#cce5ff,stroke:#339af0,color:#003566
  classDef controller fill:#ffe8cc,stroke:#ff922b,color:#7f4f24
  classDef service fill:#d3f9d8,stroke:#51cf66,color:#1b4332
  classDef repository fill:#e0f7fa,stroke:#00bcd4,color:#006064
  classDef database fill:#e6e6fa,stroke:#b39ddb,color:#4a148c
  classDef feign fill:#f1f3f5,stroke:#868e96,color:#343a40

  class A endpoint
  class B controller
  class C service
  class D repository
  class E database
  class F,G feign
```

_This diagram illustrates the layered architecture:_

- API Gateway routes requests
- InvoiceController handles HTTP requests
- Business logic sits in InvoiceService
- Data access is handled by InvoiceRepository
- Data is persisted to a MySQL database
- The service is registered with Eureka for discovery

## Component Diagram

```mermaid
flowchart LR

  subgraph Frontend [Vehicle UI]
    direction TB
    A1[Invoice UI Components]
    A2[Invoice API Client]
  end

  subgraph Backend [Spring Boot]
    direction TB
    B1[InvoiceController]
    B2[InvoiceService]
    B3[InvoiceRepository]
  end

  subgraph Database [MySQL Database]
    direction TB
    C1[(Invoice Table)]
  end

  D1[Invoice DTO]
  D2[Invoice Entity]

  A2 -->|HTTP/REST| B1
  B1 -->|Calls| B2
  B2 -->|Calls| B3
  B3 -->|Manages| C1


  B1 ---|uses| D1
  B3 ---|maps to| D2

  classDef frontend fill:#dae8fc,stroke:#6c8ebf,color:#1a237e
  classDef backend fill:#d5e8d4,stroke:#82b366,color:#1b4332
  classDef storage fill:#e8def8,stroke:#8e44ad,color:#4a148c
  classDef model fill:#fff2cc,stroke:#d6b656,color:#7f4f24

  class A1,A2 frontend
  class B1,B2,B3 backend
  class C1 storage
  class D1,D2 model
```

---

## Sequence Diagram

```mermaid
sequenceDiagram
  actor UI as Vehicle Frontend
  participant G as API Gateway
  participant C as InvoiceController
  participant S as InvoiceService
  participant B as BookingService
  participant SC as ServiceCenterService
  participant R as InvoiceRepository
  participant DB as InvoiceDB

  UI->>G: POST /api/invoices
  G->>C: Route request
  C->>S: createInvoice()
  S->>B: getBookingDetails()
  S->>SC: getServiceTypeInfo()
  S->>R: save(invoice)
  R->>DB: INSERT INTO Invoice
  R-->>S: Return Saved Invoice
  S-->>C: Return InvoiceDto
  C-->>G: Invoice Created
  G-->>UI: 201 Created (InvoiceDto)
```
## Swagger Documentation
The User Service provides interactive API documentation using Swagger.

### Access Swagger UI
Swagger UI for Invoice Service
    - http://localhost:8086/swagger-ui/index.html


---

## Run Locally

```bash
# Backend
cd invoice-service
mvn clean install
mvn spring-boot:run
```

---
