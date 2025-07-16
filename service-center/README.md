# Service Center Management Service

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

The Service Center Management Service is a Spring Boot microservice responsible for managing service centers, mechanics, and service types in the Vehicle Management System. It provides RESTful APIs for CRUD operations and integrates with other services like the ServiceCenter Service via Feign Clients. It is registered with Eureka and routed through the API Gateway.

---

## Features

- Add and manage service centers
- Register and list mechanics per center
- Define and retrieve service types
- Communicate with other services via Feign Clients
- Integrated with Eureka Discovery and API Gateway

---

## Folder Structure

```plaintext
src/
└── main/
    ├── java/
    │   └── com.vehicle.servicecenter/
    │       ├── config/           # Configuration classes (e.g., Feign, Swagger)
    │       ├── controller/       # REST controllers
    │       ├── dto/              # Data Transfer Objects
    │       ├── entity/           # JPA Entities
    │       ├── repository/       # Spring Data Repositories
    │       └── service/          # Business logic layer
    └── resources/
        └── application.properties  # App configuration
```

---

## REST API Endpoints

| Method | Endpoint                                 | Description                          |
|--------|------------------------------------------|--------------------------------------|
| POST   | `/api/service-centers`                   | Add a new service center             |
| GET    | `/api/service-centers`                   | List all service centers             |
| GET    | `/api/service-centers/{id}`              | Get service center details           |
| POST   | `/api/service-centers/{id}/mechanics`    | Add mechanic to a center             |
| GET    | `/api/service-centers/{id}/mechanics`    | List mechanics in a center           |
| POST   | `/api/service-types`                     | Define a new service type            |
| GET    | `/api/service-types`                     | List all service types               |

---

## Data Model

### ServiceCenter Entity

| Field Name        | Type           | Description                              |
|-------------------|----------------|------------------------------------------|
| `serviceCenterId` | INT            | Primary Key, unique identifier           |
| `name`            | VARCHAR(100)   | Name of the service center               |
| `location`        | VARCHAR(255)   | Physical address                         |
| `contact`         | VARCHAR(50)    | Contact number or email                  |

### Mechanic Entity

| Field Name        | Type           | Description                              |
|-------------------|----------------|------------------------------------------|
| `mechanicId`      | INT            | Primary Key, unique identifier           |
| `serviceCenterId` | INT            | Foreign Key referencing `ServiceCenterID`|
| `name`            | VARCHAR(100)   | Full name of the mechanic                |
| `expertise`       | VARCHAR(100)   | Area of specialization                   |

### ServiceType Entity

| Field Name        | Type           | Description                              |
|-------------------|----------------|------------------------------------------|
| `serviceTypeId`   | INT            | Primary Key, unique identifier           |
| `description`     | TEXT           | Description of the service               |
| `price`           | DECIMAL(10,2)  | Cost of the service                      |

---

## Module Architecture Diagram

```mermaid
flowchart LR
  A[/api/service-centers/] --> B[ServiceCenterController]
  B --> C[ServiceCenterService]
  C --> D[ServiceCenterRepository]
  D --> E[(service_center_db<br>MySQL Database)]

  classDef endpoint fill:#cce5ff,stroke:#339af0,color:#003566
  classDef controller fill:#ffe8cc,stroke:#ff922b,color:#7f4f24
  classDef service fill:#d3f9d8,stroke:#51cf66,color:#1b4332
  classDef repository fill:#e0f7fa,stroke:#00bcd4,color:#006064
  classDef database fill:#e6e6fa,stroke:#b39ddb,color:#4a148c

  class A endpoint
  class B controller
  class C service
  class D repository
  class E database
```


_This diagram illustrates the layered architecture:_

- API Gateway routes requests
- ServiceCenterController handles HTTP requests
- Business logic sits in ServiceCenterService
- Data access is handled by ServiceCenterRepository
- Data is persisted to a MySQL database
- The service is registered with Eureka for discovery


## Component Diagram

```mermaid
flowchart LR

  subgraph Frontend [Vehicle UI]
    direction TB
    A1[Service Center UI Components]
    A2[Service Center API Client]
  end

  subgraph Backend [Spring Boot]
    direction TB
    B1[ServiceCenterController]
    B2[ServiceCenterService]
    B3[ServiceCenterRepository]
  end

  subgraph Database [MysQL Database]
    direction TB
    C1[(ServiceCenter Table)]
    C2[(Mechanic Table)]
    C3[(ServiceType Table)]
  end

  D1[ServiceCenter DTO]
  D2[ServiceCenter Entity]

  A2 -->|HTTP/REST| B1
  B1 -->|Calls| B2
  B2 -->|Calls| B3
  B3 -->|Manages| C1 & C2 & C3

  B1 ---|uses| D1
  B3 ---|maps to| D2

  classDef frontend fill:#dae8fc,stroke:#6c8ebf,color:#1a237e
  classDef backend fill:#d5e8d4,stroke:#82b366,color:#1b4332
  classDef storage fill:#e8def8,stroke:#8e44ad,color:#4a148c
  classDef model fill:#fff2cc,stroke:#d6b656,color:#7f4f24

  class A1,A2 frontend
  class B1,B2,B3 backend
  class C1,C2,C3 storage
  class D1,D2 model
```

---

## Sequence Diagram

```mermaid
sequenceDiagram
  actor Admin as Admin
  participant G as API Gateway
  participant C as ServiceCenterController
  participant S as ServiceCenterService
  participant R as ServiceCenterRepository
  participant DB as ServiceCenterDB

  Admin->>G: POST /api/service-centers
  G->>C: Route request
  C->>S: addServiceCenter()
  S->>R: save(serviceCenter)
  R->>DB: INSERT INTO ServiceCenter
  R-->>S: Return Saved Entity
  S-->>C: Return DTO
  C-->>G: 201 Created
  G-->>Admin: Service center added
```

## Swagger Documentation
The User Service provides interactive API documentation using Swagger.

### Access Swagger UI
Swagger UI for User Service
    - http://localhost:8085/swagger-ui/index.html


---

## Run Locally

```bash
# Backend
cd service-center-service
mvn clean install
mvn spring-boot:run
```
