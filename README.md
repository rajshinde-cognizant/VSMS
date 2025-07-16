# 🚗 Vehicle Service Booking System (VSBS)

## 📑 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture Diagram](#architecture-diagram)
- [Module Overview](#module-overview)
  - [User Management Module](#user-management-module)
  - [Vehicle Management Module](#vehicle-management-module)
  - [Service Booking Module](#service-booking-module)
  - [Service Center Management Module](#service-center-management-module)
  - [Invoice and Billing Module](#invoice-and-billing-module)
- [Setup Instructions](#setup-instructions)
- [Testing](#testing)
- [Deployment](#deployment)
- [Contributors](#contributors)
- [License](#license)

---

## Overview

The **Vehicle Service Booking System** is a full-stack web application that allows users to:
- Book vehicle service appointments,
- Track service history and status,
- Manage service centers and available services, and
- Handle billing and invoice generation.

It supports modular microservice architecture and is developed using Java (Spring Boot) or ASP.NET Core for backend, and Angular or React for the frontend. The database layer is built on top of MySQL/PostgreSQL.

---

## Features

- **User Management**: Registration, login, profile, and service history
- **Vehicle Management**: Manage vehicles linked to user accounts
- **Service Booking**: Book, cancel, reschedule services and track status
- **Service Center Management**: Manage mechanics and service types
- **Billing and Invoicing**: Generate and track invoices and payments

---

## Tech Stack

- **Backend**: Java Spring Boot 
- **Frontend**: React.js   
- **Database**: MySQL   
- **API Documentation**: Swagger
- **Authentication** : JWT    

---

## Architecture Diagram

![image](https://github.com/user-attachments/assets/5509de9b-73bc-42dc-89d2-4d68c3b9b37e)

---
## Module Overview

Each module is designed to be modular and self-contained, managing its own responsibilities and data for better scalability, maintainability, and ease of deployment.

---

## 🚀 Main Modules

These are the core modules essential for running the Vehicle Service Booking System.

### [User Management Module](./backend/UserService/README.md)
- Handles user registration, login, profile updates, and service history tracking.
- **Entities**: `User (UserID, Name, Email, Phone, Address, PasswordHash)`

---

### [Vehicle Management Module](./backend/VehicleService/README.md)
- Allows users to register, update, and manage their vehicles, linked to their profile.
- **Entities**: `Vehicle (VehicleID, UserID, Make, Model, Year, RegistrationNumber)`

---

### [Service Booking Module](./backend/BookingService/README.md)
- Facilitates service appointment booking, rescheduling, cancellation, and status tracking.
- **Entities**: `Booking (BookingID, UserID, VehicleID, ServiceCenterID, Date, TimeSlot, Status)`

---

### [Service Center Management Module](./backend/ServiceCenterService/README.md)
- Manages service centers, assigned mechanics, and available service types.
- **Entities**:
  - `ServiceCenter (ServiceCenterID, Name, Location, Contact)`
  - `Mechanic (MechanicID, ServiceCenterID, Name, Expertise)`
  - `ServiceType (ServiceTypeID, Description, Price)`

### [Invoice and Billing Module](./backend/InvoiceService/README.md)
- Generates invoices after service completion and tracks payment status.
- **Entities**: `Invoice (InvoiceID, BookingID, ServiceTypeID, TotalAmount, PaymentStatus)`

---

## 🔧 Supporting Modules

These modules enhance the core functionality and improve scalability and system reliability.

### [Auth Service]
- Manages user authentication and token-based login using JWT.
- **Entities**: `UserCredentials (UserID, Email, PasswordHash, Role)`

---

### API Gateway
- Acts as the unified entry point to route incoming client requests to microservices.
- Handles logging, CORS, rate limiting, and cross-cutting concerns.
- 📘 **Swagger API Docs**: [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)

---

### Discovery Server (Eureka)
- Service registry where all microservices register dynamically.
- Enables inter-service discovery and communication.
- 🛰️ **Eureka Dashboard**: [`http://localhost:8761`](http://localhost:8761)

---


## Setup Instructions

### 🛠️ Prerequisites
- Java 21  
- Maven  
- Node.js (for React frontend)  
- MySQL  

### 🔧 Local Setup

#### 1. Clone the Repository

```bash
git clone <repository-url>
cd vehicle-service-booking-system
````

---

## 🧱 Database Setup

You can initialize all required MySQL databases using either a shell script or a SQL file.

### 📂 Files

* [`setup-databases.sh`](./database/setup-databases.sh): Shell script to create all required databases.
* [`setup-databases.sql`](./database/setup-databases.sql): SQL file to create and clean tables across all databases.

---

### ⚙️ Option 1: Shell Script

Run the shell script (you will be prompted for your MySQL root password):

```bash
chmod +x database/setup-databases.sh
./database/setup-databases.sh
```

---

### 🗃️ Option 2: SQL File

Execute the SQL file directly in your MySQL client:

```bash
mysql -u root -p < database/setup-databases.sql
```

---

Once complete, your system will have the following databases ready:

* `authdb`
* `userdb`
* `vehicledb`
* `bookingdb`
* `centerdb`
* `invoicedb`

> 🔐 **Note**: Ensure your MySQL user has permission to create and modify these databases.

---

## ⚙️ Configure Backend

Update the database connection properties in:

`backend/src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/vsbsdb
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```

---

## 🚀 Run the Application

#### 1. Start Backend

```bash
cd backend
mvn spring-boot:run
```

#### 2. Start Frontend

```bash
cd frontend
npm install
npm start
```

---

## 🌐 Access the Application

* **Frontend**: [http://localhost:3000](http://localhost:3000)
* **Backend APIs**: [http://localhost:8080/api](http://localhost:8080/api)

---

## Testing

Run backend unit tests:

```bash
mvn test
```

Test APIs using Postman or access Swagger UI at:

```
http://localhost:8080/swagger-ui.html
```

---

## Deployment

#### 1. Build the Application

```bash
mvn clean package
```

#### 2. Run the JAR File

```bash
java -jar target/vsbs-1.0-SNAPSHOT.jar
```

#### 3. Set Production Environment Variables

```bash
export SPRING_PROFILES_ACTIVE=prod
export DATABASE_URL=jdbc:mysql://<production-db-url>:3306/vsbsdb
export DATABASE_USERNAME=your_db_username
export DATABASE_PASSWORD=your_db_password
```

#### 4. Monitor Logs

```bash
tail -f logs/vsbs-production.log
```

---

## Contributors
- Siddhi Kate
- Rajvardhan Shinde
- Shruti Patil

---

## License

This project is licensed under the MIT License.  
See the [LICENSE](./LICENSE) file for details.
