package com.mtmanh.HotelBookingService.booking;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateBookingRequest {
    private UUID roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer adults;
    private Integer children;
    private BigDecimal totalPrice;
}