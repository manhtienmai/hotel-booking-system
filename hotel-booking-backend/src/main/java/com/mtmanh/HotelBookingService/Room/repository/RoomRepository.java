package com.mtmanh.HotelBookingService.Room.repository;

import com.mtmanh.HotelBookingService.Room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    List<Room> findByPropertyIdAndIsActiveTrue(UUID propertyId);
}