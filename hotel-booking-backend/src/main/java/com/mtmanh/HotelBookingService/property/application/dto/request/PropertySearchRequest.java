package com.mtmanh.HotelBookingService.property.application.dto.request;

import com.mtmanh.HotelBookingService.property.domain.model.PropertyType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class PropertySearchRequest {
    private String city;
    private PropertyType type;
    private Integer maxGuests;
    private BigDecimal maxPrice;

    @Builder.Default
    private int page = 0;

    @Builder.Default
    private int size = 10;
}
