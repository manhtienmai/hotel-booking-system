package com.mtmanh.HotelBookingService.booking;

import com.mtmanh.HotelBookingService.Room.RoomInventory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomInventoryRepository extends JpaRepository<RoomInventory, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ri FROM RoomInventory ri WHERE ri.room.id = :roomId")
    Optional<RoomInventory> findByRoomIdWithLock(UUID roomId);
}