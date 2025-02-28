package com.mtmanh.HotelBookingService.room.application.controller;

import com.mtmanh.HotelBookingService.room.application.dto.request.CreateRoomRequest;
import com.mtmanh.HotelBookingService.room.application.dto.response.RoomResponse;
import com.mtmanh.HotelBookingService.room.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/properties/{propertyId}/rooms")
@RequiredArgsConstructor
@Tag(name = "Room Management", description = "APIs for managing property rooms")
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    @Operation(summary = "Create a new room")
    public ResponseEntity<RoomResponse> createRoom(
            @PathVariable UUID propertyId,
            @Valid @RequestBody CreateRoomRequest request) {
        return ResponseEntity.ok(roomService.createRoom(propertyId, request));
    }

    @GetMapping
    @Operation(summary = "Get all rooms for a property")
    public ResponseEntity<List<RoomResponse>> getRoomsByProperty(
            @PathVariable UUID propertyId) {
        return ResponseEntity.ok(roomService.getRoomsByProperty(propertyId));
    }

    @PutMapping("/{roomId}")
    @Operation(summary = "Update room")
    public ResponseEntity<RoomResponse> updateRoom(
            @PathVariable UUID propertyId,
            @PathVariable UUID roomId,
            @Valid @RequestBody CreateRoomRequest request) {
        return ResponseEntity.ok(roomService.updateRoom(propertyId, roomId, request));
    }

    @DeleteMapping("/{roomId}")
    @Operation(summary = "Delete room")
    public ResponseEntity<Void> deleteRoom(
            @PathVariable UUID propertyId,
            @PathVariable UUID roomId) {
        roomService.deleteRoom(propertyId, roomId);
        return ResponseEntity.noContent().build();
    }
}
