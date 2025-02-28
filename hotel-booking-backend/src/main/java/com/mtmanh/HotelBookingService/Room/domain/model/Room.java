package com.mtmanh.HotelBookingService.room.domain.model;
import com.mtmanh.HotelBookingService.property.domain.model.Property;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "rooms")
@Data
public class Room {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    private String name;
    private String description;
    private Integer maxGuests;
    private BigDecimal price;

    @ElementCollection
    @CollectionTable(name = "room_amenities")
    private Set<String> amenities;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "base_price")
    private BigDecimal basePrice;

    @Column(name = "total_units")
    private Integer totalUnits = 1;

    @OneToMany(mappedBy = "room")
    private List<RoomAvailability> availabilities;

    @OneToOne(mappedBy = "room", cascade = CascadeType.ALL)
    private RoomInventory inventory;
}