package com.mtmanh.HotelBookingService.property.repository;

import com.mtmanh.HotelBookingService.property.entity.Property;
import com.mtmanh.HotelBookingService.property.entity.PropertyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PropertyRepository extends JpaRepository<Property, UUID> {
    Page<Property> findByIsActiveTrue(Pageable pageable);
    List<Property> findByAddressCityAndIsActiveTrue(String city);
    List<Property> findByTypeAndIsActiveTrue(PropertyType type);
}
