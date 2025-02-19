package com.mtmanh.HotelBookingService;

import org.springframework.security.access.prepost.PreAuthorize;

public class demo {
    @PreAuthorize("hasRole('client_user')")
    public String hello() {
        return "hello";
    }
}
