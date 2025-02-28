package com.mtmanh.HotelBookingService.booking;

import com.mtmanh.HotelBookingService.booking.domain.repository.RoomAvailabilityRepository;
import com.mtmanh.HotelBookingService.booking.domain.repository.RoomInventoryRepository;
import com.mtmanh.HotelBookingService.room.domain.model.*;
import com.mtmanh.HotelBookingService.room.domain.repository.RoomRepository;
import com.mtmanh.HotelBookingService.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomAvailabilityService {
    private final RoomAvailabilityRepository availabilityRepository;
    private final RoomInventoryRepository inventoryRepository;
    private final RoomRepository roomRepository;

    public boolean isRoomAvailable(UUID roomId, LocalDate checkIn, LocalDate checkOut, int guests) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        if (guests > room.getMaxGuests()) {
            return false;
        }

        List<RoomAvailability> availabilities = availabilityRepository
                .findAvailabilitiesForDateRange(roomId, checkIn, checkOut);

        if (availabilities.isEmpty()) {
            return false;
        }

        return availabilities.stream()
                .allMatch(availability ->
                        availability.getIsAvailable() &&
                                availability.getAvailableUnits() > 0
                );
    }

    public void updateAvailability(UUID roomId, LocalDate startDate, LocalDate endDate, boolean isAvailable) {
        List<RoomAvailability> availabilities = availabilityRepository
                .lockAvailabilitiesForDateRange(roomId, startDate, endDate);

        RoomInventory inventory = inventoryRepository
                .findByRoomIdWithLock(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room inventory not found"));

        for (RoomAvailability availability : availabilities) {
            availability.setIsAvailable(isAvailable);
            if (isAvailable) {
                availability.setAvailableUnits(inventory.getTotalUnits() - inventory.getBookedUnits());
            } else {
                availability.setAvailableUnits(0);
            }
        }

        availabilityRepository.saveAll(availabilities);
    }

    public void initializeAvailability(Room room, LocalDate startDate, LocalDate endDate) {
        LocalDate currentDate = startDate;
        List<RoomAvailability> availabilities = new ArrayList<>();

        while (!currentDate.isAfter(endDate)) {
            RoomAvailability availability = new RoomAvailability();
            availability.setRoom(room);
            availability.setDate(currentDate);
            availability.setPricePerNight(room.getBasePrice());
            availability.setIsAvailable(true);
            availability.setAvailableUnits(room.getTotalUnits());
            availabilities.add(availability);
            currentDate = currentDate.plusDays(1);
        }

        availabilityRepository.saveAll(availabilities);
    }

    public void setRoomAvailability(RoomAvailabilitySettings settings) {
        Room room = roomRepository.findById(settings.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        List<RoomAvailability> availabilities = new ArrayList<>();
        LocalDate currentDate = settings.getStartDate();

        while (!currentDate.isAfter(settings.getEndDate())) {
            RoomAvailability availability = new RoomAvailability();
            availability.setRoom(room);
            availability.setDate(currentDate);

            // Check for special dates first
            Optional<SpecialDate> specialDate = findSpecialDate(settings, currentDate);
            if (specialDate.isPresent()) {
                availability.setIsAvailable(specialDate.get().isAvailable());
                availability.setPricePerNight(specialDate.get().getPrice());
            }
            // Then check if it's weekend
            else if (isWeekend(currentDate) && settings.getWeekendPrice() != null) {
                availability.setIsAvailable(settings.isAvailable());
                availability.setPricePerNight(settings.getWeekendPrice());
            }
            // Use base price
            else {
                availability.setIsAvailable(settings.isAvailable());
                availability.setPricePerNight(settings.getBasePrice());
            }

            availabilities.add(availability);
            currentDate = currentDate.plusDays(1);
        }

        availabilityRepository.saveAll(availabilities);
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private Optional<SpecialDate> findSpecialDate(RoomAvailabilitySettings settings, LocalDate date) {
        if (settings.getSpecialDates() == null) return Optional.empty();

        return settings.getSpecialDates().stream()
                .filter(sd -> sd.getDate().equals(date))
                .findFirst();
    }
}
