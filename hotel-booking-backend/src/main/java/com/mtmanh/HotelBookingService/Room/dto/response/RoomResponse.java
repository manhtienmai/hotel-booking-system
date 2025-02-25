package com.mtmanh.HotelBookingService.Room.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
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