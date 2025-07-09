package com.example.booking_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import com.example.booking_service.client.UserClient;

@SpringBootTest
// @ComponentScan(excludeFilters = {
//     @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = UserClient.class)
// })
public class BookingServiceApplicationTests {
}
