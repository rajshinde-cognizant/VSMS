package com.example.booking_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
 
import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
 
@Configuration
public class FeignClientConfig {
 
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String authHeader = request.getHeader("Authorization");
                if (authHeader != null) {
                    template.header("Authorization", authHeader);
                }
            }
        };
    }
}

// import feign.RequestInterceptor;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.core.context.SecurityContextHolder;
 
// @Configuration
// public class FeignClientConfig {
 
//     @Bean
//     public RequestInterceptor requestInterceptor() {
//         return requestTemplate -> {
//             var auth = SecurityContextHolder.getContext().getAuthentication();
//             if (auth != null && auth.getCredentials() instanceof String token) {
//                 requestTemplate.header("Authorization", "Bearer " + token);
//             }
//         };
//     }
// }