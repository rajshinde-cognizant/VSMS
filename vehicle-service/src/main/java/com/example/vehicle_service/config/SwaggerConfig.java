package com.example.vehicle_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;
 
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Vehicle Service API",
        version = "1.0",
        description = "APIs for managing vehicle operations"
    )
)
public class SwaggerConfig {
    // No need for beans, Springdoc auto-configures OpenAPI 3
}
