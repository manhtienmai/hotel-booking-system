package com.mtmanh.HotelBookingService.property.application.controller;

import com.mtmanh.HotelBookingService.property.application.dto.request.CreatePropertyRequest;
import com.mtmanh.HotelBookingService.property.application.dto.response.PropertyResponse;
import com.mtmanh.HotelBookingService.property.application.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
@Tag(name = "Property Management", description = "APIs for managing properties")
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping
    @Operation(summary = "Create a new property")
    public ResponseEntity<PropertyResponse> createProperty(
            @Valid @RequestBody CreatePropertyRequest request) {
        return ResponseEntity.ok(propertyService.createProperty(request));
    }

    @GetMapping
    @Operation(summary = "Get all properties")
    public ResponseEntity<Page<PropertyResponse>> getAllProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(propertyService.getAllProperties(page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get property by ID")
    public ResponseEntity<PropertyResponse> getPropertyById(@PathVariable UUID id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update property")
    public ResponseEntity<PropertyResponse> updateProperty(
            @PathVariable UUID id,
            @Valid @RequestBody CreatePropertyRequest request) {
        return ResponseEntity.ok(propertyService.updateProperty(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete property")
    public ResponseEntity<Void> deleteProperty(@PathVariable UUID id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/search")
//    @Operation(summary = "Search properties")
//    public ResponseEntity<Page<PropertyResponse>> searchProperties(
//            @Valid PropertySearchRequest request) {
//        return ResponseEntity.ok(propertyService.searchProperties(request));
//    }
}