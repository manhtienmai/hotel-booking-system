package com.mtmanh.HotelBookingService.property.dto.response;

import com.mtmanh.HotelBookingService.Room.dto.response.RoomResponse;
import com.mtmanh.HotelBookingService.property.dto.AddressDTO;
import com.mtmanh.HotelBookingService.property.entity.PropertyType;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class PropertyResponse {
    private UUID id;
    private String name;
    private PropertyType type;
    private String description;
    private AddressDTO address;
    private String contactPhone;
    private String contactEmail;
    private Set<String> amenities;
    private List<RoomResponse> rooms;
    private List<String> imageUrls;
    private LocalDateTime createdAt;
}