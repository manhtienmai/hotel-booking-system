package com.mtmanh.HotelBookingService.property.dto.request;

import com.mtmanh.HotelBookingService.Room.dto.request.CreateRoomRequest;
import com.mtmanh.HotelBookingService.property.dto.AddressDTO;
import com.mtmanh.HotelBookingService.property.entity.PropertyType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CreatePropertyRequest {
    @NotBlank(message = "property name is required")
    @Size(max = 255, message = "property name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Type is required")
    private PropertyType type;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Address is required")
    private AddressDTO address;

    @Email(message = "Invalid email format")
    private String contactEmail;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "wrong format phone")
    private String contactPhone;

    private Set<String> amenities;
    private List<CreateRoomRequest> rooms;
}
