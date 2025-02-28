package com.mtmanh.HotelBookingService.booking.domain.repository;

import com.mtmanh.HotelBookingService.booking.domain.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByRoomIdAndCheckInBetweenOrCheckOutBetween(
            UUID roomId,
            LocalDate checkInStart,
            LocalDate checkInEnd,
            LocalDate checkOutStart,
            LocalDate checkOutEnd
    );
}