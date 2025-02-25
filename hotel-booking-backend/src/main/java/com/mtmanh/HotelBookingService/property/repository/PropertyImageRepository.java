package com.mtmanh.HotelBookingService.property.repository;

import com.mtmanh.HotelBookingService.property.entity.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PropertyImageRepository extends JpaRepository<PropertyImage, UUID> {
    List<PropertyImage> findByPropertyId(UUID propertyId);
    Optional<PropertyImage> findByPropertyIdAndIsPrimaryTrue(UUID propertyId);
}
