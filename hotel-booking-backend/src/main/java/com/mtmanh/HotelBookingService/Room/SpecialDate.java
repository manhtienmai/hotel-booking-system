package com.mtmanh.HotelBookingService.Room;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SpecialDate {
    private LocalDate date;
    private BigDecimal price;
    private boolean isAvailable = true;
    private String note;
}