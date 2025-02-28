package com.mtmanh.HotelBookingService.booking.application.controller;

import com.mtmanh.HotelBookingService.booking.domain.model.Booking;
import com.mtmanh.HotelBookingService.booking.domain.service.BookingService;
import com.mtmanh.HotelBookingService.booking.application.dto.request.CreateBookingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody CreateBookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }
}