package com.mtmanh.HotelBookingService.mapper;

import com.mtmanh.HotelBookingService.property.dto.request.CreatePropertyRequest;
import com.mtmanh.HotelBookingService.property.dto.response.PropertyResponse;
import com.mtmanh.HotelBookingService.property.entity.Property;
import com.mtmanh.HotelBookingService.property.entity.PropertyImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PropertyMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Property toEntity(CreatePropertyRequest request);

    @Mapping(target = "rooms", source = "rooms")
    @Mapping(target = "imageUrls", expression = "java(mapImageUrls(property.getImages()))")
    PropertyResponse toResponse(Property property);

    void updateEntity(CreatePropertyRequest request, @MappingTarget Property property);

    default List<String> mapImageUrls(List<PropertyImage> images) {
        if (images == null) return new ArrayList<>();
        return images.stream()
                .map(PropertyImage::getUrl)
                .collect(Collectors.toList());
    }
}