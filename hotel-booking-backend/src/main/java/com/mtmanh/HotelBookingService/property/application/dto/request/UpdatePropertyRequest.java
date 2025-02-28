package com.mtmanh.HotelBookingService.property.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdatePropertyRequest {
    @Size(max = 255, message = "property name must not exceed 255 characters")
    private String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @Min(value = 1, message = "Star rating must be between 1 and 5")
    @Max(value = 5, message = "Star rating must be between 1 and 5")
    private Integer starRating;

    @Email(message = "Invalid email format")
    private String contactEmail;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number must be in international format")
    private String contactPhone;

    private Boolean active;
}