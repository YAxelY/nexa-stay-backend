package com.nexastay.roomservice.service.interfaces;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.nexastay.roomservice.dto.RoomDto;
import com.nexastay.roomservice.model.Room;
import com.nexastay.roomservice.request.AddRoomRequest;
import com.nexastay.roomservice.request.UpdateRoomRequest;

public interface IRoomService {
    Room addRoom(AddRoomRequest room);

    Room getRoomById(UUID id);

    Room updateRoomById(UpdateRoomRequest room, UUID id);

    Room getRoomByName(String name);

    void deleteRoomById(UUID id);

    List<Room> getAllRoom();

    List<Room> getRoomByCapacity(Integer capacity);

    List<Room> getRoomByType(String type);

    List<Room> getRoomByStatus(String status);

    List<Room> getRoomByPrice(BigDecimal price);

    RoomDto convertRoomToDto(Room room);
}
