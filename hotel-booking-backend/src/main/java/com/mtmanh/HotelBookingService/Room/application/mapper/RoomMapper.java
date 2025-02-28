package com.mtmanh.HotelBookingService.room.application.mapper;

import com.mtmanh.HotelBookingService.room.domain.model.Room;
import com.mtmanh.HotelBookingService.room.application.dto.request.CreateRoomRequest;
import com.mtmanh.HotelBookingService.room.application.dto.response.RoomResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "property", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    Room toEntity(CreateRoomRequest request);

    RoomResponse toResponse(Room room);

    void updateEntity(CreateRoomRequest request, @MappingTarget Room room);
}