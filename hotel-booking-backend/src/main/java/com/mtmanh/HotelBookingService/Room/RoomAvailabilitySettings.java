package com.mtmanh.HotelBookingService.Room;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class RoomAvailabilitySettings {
    private UUID roomId;
    private LocalDate startDate;
    private LocalDate endDate;

    private BigDecimal basePrice;
    private boolean isAvailable = true;

    private BigDecimal weekendPrice;

    private List<SpecialDate> specialDates;
}