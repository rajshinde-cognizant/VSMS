package com.example.user_service.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String phone;
    private String address;
}