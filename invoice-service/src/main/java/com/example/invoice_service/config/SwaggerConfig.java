package com.example.invoice_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
@Configuration
public class SwaggerConfig {
 
    @Bean
    public OpenAPI bookingOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Invoice Service API")
                .description("API documentation for Invoice microservice")
                .version("v1.0"));
    }
}