package com.mtmanh.HotelBookingService.room.application.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
public class RoomResponse {
    private UUID id;
    private String name;
    private String description;
    private Integer maxGuests;
    private BigDecimal price;
    private Set<String> amenities;
}