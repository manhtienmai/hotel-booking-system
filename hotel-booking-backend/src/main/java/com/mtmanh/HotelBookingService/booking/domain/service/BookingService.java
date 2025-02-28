package com.mtmanh.HotelBookingService.booking.domain.service;

import com.mtmanh.HotelBookingService.booking.RoomAvailabilityService;
import com.mtmanh.HotelBookingService.booking.application.dto.request.CreateBookingRequest;
import com.mtmanh.HotelBookingService.booking.domain.model.Booking;
import com.mtmanh.HotelBookingService.booking.domain.model.BookingStatus;
import com.mtmanh.HotelBookingService.booking.domain.repository.BookingRepository;
import com.mtmanh.HotelBookingService.booking.domain.repository.RoomInventoryRepository;
import com.mtmanh.HotelBookingService.room.domain.model.RoomInventory;
import com.mtmanh.HotelBookingService.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomAvailabilityService availabilityService;
    private final RoomInventoryRepository inventoryRepository;

    public Booking createBooking(CreateBookingRequest request) {
        // Validate dates
        if (request.getCheckIn().isBefore(LocalDate.now()) ||
                request.getCheckOut().isBefore(request.getCheckIn())) {
            throw new IllegalArgumentException("Invalid dates");
        }

        // Check availability with pessimistic locking
        if (!availabilityService.isRoomAvailable(
                request.getRoomId(),
                request.getCheckIn(),
                request.getCheckOut(),
                request.getAdults() + request.getChildren()
        )) {
            throw new ResourceNotFoundException("Room is not available for selected dates");
        }

        // Lock inventory for update
        RoomInventory inventory = inventoryRepository
                .findByRoomIdWithLock(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room inventory not found"));

        // Create booking
        Booking booking = new Booking();
        booking.setRoom(inventory.getRoom());
        booking.setCheckIn(request.getCheckIn());
        booking.setCheckOut(request.getCheckOut());
        booking.setAdults(request.getAdults());
        booking.setChildren(request.getChildren());
        booking.setTotalPrice(request.getTotalPrice());
        booking.setStatus(BookingStatus.CONFIRMED);

        // Update inventory
        inventory.setBookedUnits(inventory.getBookedUnits() + 1);
        inventoryRepository.save(inventory);

        // Update availability
        availabilityService.updateAvailability(
                request.getRoomId(),
                request.getCheckIn(),
                request.getCheckOut(),
                false
        );

        return bookingRepository.save(booking);
    }
}
