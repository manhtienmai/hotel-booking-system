package com.mtmanh.HotelBookingService.booking;

import com.mtmanh.HotelBookingService.Room.RoomAvailability;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface RoomAvailabilityRepository extends JpaRepository<RoomAvailability, UUID> {
    @Query("SELECT ra FROM RoomAvailability ra " +
            "WHERE ra.room.id = :roomId " +
            "AND ra.date BETWEEN :startDate AND :endDate")
    List<RoomAvailability> findAvailabilitiesForDateRange(
            UUID roomId,
            LocalDate startDate,
            LocalDate endDate
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ra FROM RoomAvailability ra " +
            "WHERE ra.room.id = :roomId " +
            "AND ra.date BETWEEN :startDate AND :endDate ")
    List<RoomAvailability> lockAvailabilitiesForDateRange(
            UUID roomId,
            LocalDate startDate,
            LocalDate endDate
    );
}