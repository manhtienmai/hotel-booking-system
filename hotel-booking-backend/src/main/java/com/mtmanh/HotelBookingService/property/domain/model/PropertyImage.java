package com.mtmanh.HotelBookingService.property.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "property_images")
@Data
public class PropertyImage {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    private String url;
    private String caption;
    private Boolean isPrimary = false;
}