package com.mtmanh.HotelBookingService.room.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "room_inventory")
@Data
public class RoomInventory {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;

    private Integer totalUnits;
    private Integer bookedUnits;
    private Integer maintenanceUnits;

    @Version
    private Long version;
}