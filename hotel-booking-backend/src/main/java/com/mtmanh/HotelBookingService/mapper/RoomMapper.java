package com.mtmanh.HotelBookingService.mapper;

import com.mtmanh.HotelBookingService.Room.Room;
import com.mtmanh.HotelBookingService.Room.dto.request.CreateRoomRequest;
import com.mtmanh.HotelBookingService.Room.dto.response.RoomResponse;
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