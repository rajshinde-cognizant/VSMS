#!/bin/bash

# Navigate to the eureka-discovery-service directory and run the service
cd eureka-discovery-service
mvn clean install
mvn spring-boot:run &

# Navigate to the api-gatway directory and run the service
cd ../api-gatway
mvn clean install
mvn spring-boot:run &

# Navigate to the auth-service directory and run the service
cd ../auth-service
mvn clean install
mvn spring-boot:run &

# Navigate to the booking-service directory and run the service
cd ../booking-service
mvn clean install
mvn spring-boot:run &

# Navigate to the invoice-service directory and run the service
cd ../invoice-service
mvn clean install
mvn spring-boot:run &

# Navigate to the service-center directory and run the service
cd ../service-center
mvn clean install
mvn spring-boot:run &

# Navigate to the user-service directory and run the service
cd ../user-service
mvn clean install
mvn spring-boot:run &

# Navigate to the vehicle-service directory and run the service
cd ../vehicle-service
mvn clean install
mvn spring-boot:run &

# Wait for all background processes to finish
wait
