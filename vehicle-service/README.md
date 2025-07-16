# Vehicle Service

## Contributor
- Siddhi Kate

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
The Vehicle Service is a Spring Boot microservice within the Vehicle Management System. It handles vehicle registration, updates, retrieval, and deletion. It communicates with the User Service via Feign Client and is registered with Eureka for service discovery.

---

## Features

- Register new vehicles with user association
- Update and delete vehicle records
- Retrieve vehicle details by ID or user
- Integrated with User Service via Feign Client
- Registered with Eureka Discovery
- Routed via API Gateway

---

## Folder Structure

```plaintext
src/
└── main/
    ├── java/
    │   └── com.vehicle.service/
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

| Method | Endpoint                                | Description                          |
|--------|-----------------------------------------|--------------------------------------|
| POST   | `/api/vehicles/`                        | Add a new vehicle                    |
| GET    | `/api/vehicles/user/{userId}`           | Retrieve vehicles by user ID         |
| GET    | `/api/vehicles/{vehicleId}`             | Retrieve vehicle by vehicle ID       |
| PUT    | `/api/vehicles/{vehicleId}`             | Update vehicle details               |
| DELETE | `/api/vehicles/{vehicleId}`             | Delete a vehicle                     |

---

## Data Model

### Vehicle Entity

| Field Name          | Data Type      | Description                              |
|---------------------|----------------|------------------------------------------|
| `vehicleId`         | BIGINT         | Primary Key, auto-generated              |
| `userId`            | BIGINT         | Foreign Key referencing User             |
| `make`              | VARCHAR(255)   | Manufacturer of the vehicle              |
| `model`             | VARCHAR(255)   | Model name of the vehicle                |
| `year`              | INT            | Manufacturing year                       |
| `registrationNumber`| VARCHAR(50)    | Unique registration number               |

---

## Module Architecture Diagram

```mermaid
flowchart LR
  A[/api/vehicles/] --> B[VehicleController]
  B --> C[VehicleService]
  C --> D[VehicleRepository]
  D --> E[(vehicle_db<br>MySQL Database)]
  C --> F[FeignClient: UserService]

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
  class F feign
```

_This diagram illustrates the layered architecture:_

- API Gateway routes requests  
- VehicleController handles HTTP requests  
- Business logic sits in VehicleService  
- Data access is handled by VehicleRepository  
- Data is persisted to an MySQL database  
- The service is registered with Eureka for discovery

---

## Component Diagram

```mermaid
flowchart LR

  subgraph Frontend [Vehicle UI]
    direction TB
    A1[Vehicle UI Components]
    A2[Vehicle API Client]
  end

  subgraph Backend [Spring Boot]
    direction TB
    B1[VehicleController]
    B2[VehicleService]
    B3[VehicleRepository]
  end

  subgraph Database [MySQL Database]
    direction TB
    C1[(Vehicle Table)]
  end

  D1[Vehicle DTO]
  D2[Vehicle Entity]

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

### Vehicle Registration

```mermaid
sequenceDiagram
  actor UI as Vehicle Frontend
  participant G as API Gateway
  participant C as VehicleController
  participant S as VehicleService
  participant R as VehicleRepository
  participant DB as VehicleDB

  UI->>G: POST /api/vehicles/
  G->>C: Route request
  C->>S: registerVehicle()
  S->>R: save(vehicle)
  R->>DB: INSERT INTO Vehicle
  R-->>S: Return Saved Vehicle
  S-->>C: Return VehicleDto
  C-->>G: Vehicle Created
  G-->>UI: 201 Created (VehicleDto)
```

---

## Swagger Documentation

The Vehicle Service provides interactive API documentation using Swagger.

### Access Swagger UI

- Swagger UI for Vehicle Service
    http://localhost:8083/swagger-ui/index.html

---

## Run Locally

```bash
# Backend
cd vehicle-service
mvn clean install
mvn spring-boot:run
```
