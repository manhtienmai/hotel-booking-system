package com.mtmanh.HotelBookingService.booking.application.controller;

import com.mtmanh.HotelBookingService.booking.RoomAvailabilityService;
import com.mtmanh.HotelBookingService.room.domain.model.RoomAvailabilitySettings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomAvailabilityController {
    private final RoomAvailabilityService availabilityService;

    @GetMapping("/{roomId}/availability")
    public ResponseEntity<Boolean> checkAvailability(
            @PathVariable UUID roomId,
            @RequestParam LocalDate checkIn,
            @RequestParam LocalDate checkOut,
            @RequestParam(defaultValue = "1") int guests
    ) {
        return ResponseEntity.ok(
                availabilityService.isRoomAvailable(roomId, checkIn, checkOut, guests)
        );
    }

    @PostMapping("/{roomId}/availability")
    public ResponseEntity<Void> setRoomAvailability(@RequestBody RoomAvailabilitySettings settings) {
        availabilityService.setRoomAvailability(settings);
        return ResponseEntity.ok().build();
    }
}