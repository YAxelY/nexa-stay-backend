package com.nexastay.roomservice.mapper;

import com.nexastay.roomservice.dto.RoomDto;
import com.nexastay.roomservice.model.Room;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomMapper {
    private final ModelMapper modelMapper;

    public RoomMapper() {
        this.modelMapper = new ModelMapper();
        configureModelMapper();
    }

    private void configureModelMapper() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setPropertyCondition(Conditions.isNotNull());

        // Configure type mappings
        TypeMap<Room, RoomDto> typeMap = modelMapper.createTypeMap(Room.class, RoomDto.class);
        typeMap.addMapping(Room::getName, RoomDto::setName);
        typeMap.addMapping(Room::getId, RoomDto::setId);
        typeMap.addMapping(Room::getDescription, RoomDto::setDescription);
        typeMap.addMapping(Room::getImageUrl, RoomDto::setImageUrl);
        typeMap.addMapping(Room::getPrice, RoomDto::setPrice);
        typeMap.addMapping(Room::getCapacity, RoomDto::setCapacity);
        typeMap.addMapping(Room::getType, RoomDto::setType);
        typeMap.addMapping(Room::getStatus, RoomDto::setStatus);
    }

    public RoomDto convertRoomToDto(Room room) {
        return modelMapper.map(room, RoomDto.class);
    }

    public List<RoomDto> convertRoomToListDto(List<Room> rooms) {
        return rooms.stream()
                .map(this::convertRoomToDto)
                .collect(Collectors.toList());
    }
}
