package com.mtmanh.HotelBookingService.property.entity;

import com.mtmanh.HotelBookingService.Room.Room;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "properties")
@Data
public class Property {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private PropertyType type;  // HOTEL, HOMESTAY, APARTMENT

    @Column(columnDefinition = "TEXT")
    private String description;

    @Embedded
    private Address address;

    private String contactPhone;
    private String contactEmail;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @ElementCollection
    @CollectionTable(name = "property_amenities")
    private Set<String> amenities;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Room> rooms;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<PropertyImage> images;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}