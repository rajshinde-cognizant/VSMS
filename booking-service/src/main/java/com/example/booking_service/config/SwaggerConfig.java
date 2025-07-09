package com.example.booking_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
@Configuration
public class SwaggerConfig {
 
    @Bean
    public OpenAPI bookingOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Booking Service API")
                .description("API documentation for Booking microservice")
                .version("v1.0"));
    }
}