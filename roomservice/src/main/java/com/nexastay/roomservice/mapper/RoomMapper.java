package com.nexastay.roomservice.mapper;


import com.nexastay.roomservice.dto.RoomDto;
import com.nexastay.roomservice.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    RoomDto convertRoomToDto(Room room);

    List<RoomDto> convertRoomToListDto(List<Room> rooms);

    Room toEntity(RoomDto roomDto); // Convert RoomDto to entity
}
