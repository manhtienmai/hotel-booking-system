package com.mtmanh.HotelBookingService.room.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.UUID;
import java.math.BigDecimal;
import java.util.List;

@Data
public class UpdateRoomRequest {
    @Min(value = 1, message = "Total rooms must be at least 1")
    private Integer totalRooms;

    @Min(value = 0, message = "Available rooms must not be negative")
    private Integer availableRooms;

    private List<UUID> amenityIds;

    @DecimalMin(value = "0.0", message = "Base price must not be negative")
    private BigDecimal basePrice;

    private Boolean active;
}