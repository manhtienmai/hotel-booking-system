package com.mtmanh.HotelBookingService.Room;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "room_availability")
@Data
public class RoomAvailability {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(nullable = false)
    private LocalDate date;

    private Integer availableUnits;

    private BigDecimal pricePerNight;

    @Column(name = "is_available")
    private Boolean isAvailable = true;

    @Version
    private Long version;
}