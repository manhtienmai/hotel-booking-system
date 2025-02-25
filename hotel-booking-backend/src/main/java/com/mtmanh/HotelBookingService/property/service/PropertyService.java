package com.mtmanh.HotelBookingService.property.service;

import com.mtmanh.HotelBookingService.Room.Room;
import com.mtmanh.HotelBookingService.Room.repository.RoomRepository;
import com.mtmanh.HotelBookingService.exception.ResourceNotFoundException;
import com.mtmanh.HotelBookingService.mapper.RoomMapper;
import com.mtmanh.HotelBookingService.mapper.PropertyMapper;
import com.mtmanh.HotelBookingService.property.dto.request.CreatePropertyRequest;
import com.mtmanh.HotelBookingService.property.dto.request.PropertySearchRequest;
import com.mtmanh.HotelBookingService.property.dto.response.PropertyResponse;
import com.mtmanh.HotelBookingService.property.entity.Property;
import com.mtmanh.HotelBookingService.property.repository.PropertyImageRepository;
import com.mtmanh.HotelBookingService.property.repository.PropertyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final RoomRepository roomRepository;
    private final PropertyImageRepository imageRepository;
    private final PropertyMapper propertyMapper;
    private final RoomMapper roomMapper;

    public PropertyResponse createProperty(CreatePropertyRequest request) {
        Property property = propertyMapper.toEntity(request);
        final Property savedProperty = propertyRepository.save(property);

        // Save rooms if provided
        if (request.getRooms() != null) {
            List<Room> rooms = request.getRooms().stream()
                    .map(roomRequest -> {
                        Room room = roomMapper.toEntity(roomRequest);
                        room.setProperty(savedProperty);
                        return room;
                    })
                    .collect(Collectors.toList());
            roomRepository.saveAll(rooms);
        }

        return propertyMapper.toResponse(property);
    }

    public Page<PropertyResponse> getAllProperties(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return propertyRepository.findByIsActiveTrue(pageRequest)
                .map(propertyMapper::toResponse);
    }

    public PropertyResponse getPropertyById(UUID id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));
        return propertyMapper.toResponse(property);
    }

    public PropertyResponse updateProperty(UUID id, CreatePropertyRequest request) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));

        propertyMapper.updateEntity(request, property);
        property = propertyRepository.save(property);
        return propertyMapper.toResponse(property);
    }

    public void deleteProperty(UUID id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));
        property.setIsActive(false);
        propertyRepository.save(property);
    }

//    public Page<PropertyResponse> searchProperties(PropertySearchRequest request) {
//        PageRequest pageRequest = PageRequest.of(
//                request.getPage(),
//                request.getSize(),
//                Sort.by("createdAt").descending()
//        );
//
//        Specification<Property> spec = PropertySpecifications.createSpecification(request);
//        return propertyRepository.findAll(spec, pageRequest)
//                .map(propertyMapper::toResponse);
//    }
}