package com.example.auth_service.dto;

import lombok.*;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
