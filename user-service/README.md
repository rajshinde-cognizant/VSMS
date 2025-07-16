# User Service

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
The User Service is a Spring Boot microservice within the Vehicle Management System. It handles user registration, profile management, and vehicle association. It communicates with the Vehicle Service via Feign Client and is registered with Eureka for service discovery.

---

## Features

- Register new users with personal details
- View and update user profiles
- Associate users with vehicles
- Retrieve service history linked to users
- Search users by email or list all users
- Integrated with Eureka Discovery
- Routed via API Gateway

---

## Folder Structure

```plaintext
src/
└── main/
    ├── java/
    │   └── com.vehicle.user/
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

| Method | Endpoint                          | Description                                |
|--------|-----------------------------------|--------------------------------------------|
| POST   | `/api/users/`                     | Register a new user                        |
| GET    | `/api/users/{email}`              | Retrieve user by email                     |
| GET    | `/api/users`                      | Retrieve all users                         |
| GET    | `/api/users/{userId}/vehicles`    | Retrieve vehicles associated with a user   |

---

## Data Model

### User Entity

| Field Name     | Data Type      | Description                              |
|----------------|----------------|------------------------------------------|
| `userId`       | BIGINT         | Primary Key, auto-generated              |
| `name`         | VARCHAR(255)   | Name of the user                         |
| `email`        | VARCHAR(255)   | Must be unique                           |
| `phone`        | VARCHAR(15)    | Phone number                             |
| `address`      | VARCHAR(255)   | Address of the user                      |
| `passwordHash` | VARCHAR(255)   | Hashed password                          |

---

## Module Architecture Diagram

```mermaid
flowchart LR
  A[/api/users/] --> B[UserController]
  B --> C[UserService]
  C --> D[UserRepository]
  D --> E[(user_db<br>MySQL Database)]

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
- UserController handles HTTP requests  
- Business logic sits in UserService  
- Data access is handled by UserRepository  
- Data is persisted to an MySQL database  
- The service is registered with Eureka for discovery

---

## Component Diagram

```mermaid
flowchart LR

  subgraph Frontend [Vehicle UI]
    direction TB
    A1[User UI Components]
    A2[User API Client]
  end

  subgraph Backend [Spring Boot]
    direction TB
    B1[UserController]
    B2[UserService]
    B3[UserRepository]
  end

  subgraph Database [MySQL Database]
    direction TB
    C1[(User Table)]
  end

  D1[User DTO]
  D2[User Entity]

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

### User Registration

```mermaid
sequenceDiagram
  actor UI as Vehicle Frontend
  participant G as API Gateway
  participant C as UserController
  participant S as UserService
  participant R as UserRepository
  participant DB as UserDB

  UI->>G: POST /api/users/
  G->>C: Route request
  C->>S: registerUser()
  S->>R: save(user)
  R->>DB: INSERT INTO User
  R-->>S: Return Saved User
  S-->>C: Return UserDto
  C-->>G: User Created
  G-->>UI: 201 Created (UserDto)
```

---

## Swagger Documentation

The User Service provides interactive API documentation using Swagger.

### Access Swagger UI

- Swagger UI for User Service-
      http://localhost:8082/swagger-ui/index.html

---

## Run Locally

```bash
# Backend
cd user-service
mvn clean install
mvn spring-boot:run
```

---
