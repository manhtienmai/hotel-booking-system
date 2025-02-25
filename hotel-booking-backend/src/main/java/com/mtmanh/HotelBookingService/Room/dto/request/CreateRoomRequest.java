package com.mtmanh.HotelBookingService.Room.dto.request;


import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class CreateRoomRequest {

    @NotBlank(message = "Room name is required")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Max guests is required")
    private Integer maxGuests;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    private Set<String> amenities;
}