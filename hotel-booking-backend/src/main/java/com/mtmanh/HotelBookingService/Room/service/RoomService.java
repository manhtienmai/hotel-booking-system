package com.mtmanh.HotelBookingService.room.service;

import com.mtmanh.HotelBookingService.room.domain.model.Room;
import com.mtmanh.HotelBookingService.room.application.dto.request.CreateRoomRequest;
import com.mtmanh.HotelBookingService.room.application.dto.response.RoomResponse;
import com.mtmanh.HotelBookingService.room.domain.repository.RoomRepository;
import com.mtmanh.HotelBookingService.exception.ResourceNotFoundException;
import com.mtmanh.HotelBookingService.room.application.mapper.RoomMapper;
import com.mtmanh.HotelBookingService.property.domain.model.Property;
import com.mtmanh.HotelBookingService.property.domain.repository.PropertyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final PropertyRepository propertyRepository;
    private final RoomMapper roomMapper;

    public RoomResponse createRoom(UUID propertyId, CreateRoomRequest request) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));

        Room room = roomMapper.toEntity(request);
        room.setProperty(property);
        room = roomRepository.save(room);
        return roomMapper.toResponse(room);
    }

    public List<RoomResponse> getRoomsByProperty(UUID propertyId) {
        return roomRepository.findByPropertyIdAndIsActiveTrue(propertyId).stream()
                .map(roomMapper::toResponse)
                .collect(Collectors.toList());
    }

    public RoomResponse updateRoom(UUID propertyId, UUID roomId, CreateRoomRequest request) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        if (!room.getProperty().getId().equals(propertyId)) {
            throw new IllegalArgumentException("Room does not belong to the property");
        }

        roomMapper.updateEntity(request, room);
        room = roomRepository.save(room);
        return roomMapper.toResponse(room);
    }

    public void deleteRoom(UUID propertyId, UUID roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        if (!room.getProperty().getId().equals(propertyId)) {
            throw new IllegalArgumentException("Room does not belong to the property");
        }

        room.setIsActive(false);
        roomRepository.save(room);
    }
}