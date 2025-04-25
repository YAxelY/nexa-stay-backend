package com.nexastay.roomservice.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.nexastay.roomservice.dto.RoomDto;
import com.nexastay.roomservice.model.enums.RoomStatus;
import com.nexastay.roomservice.model.enums.RoomType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexastay.roomservice.exceptions.ResourceNotFoundException;
import com.nexastay.roomservice.model.Room;
import com.nexastay.roomservice.repository.RoomRepository;
import com.nexastay.roomservice.request.AddRoomRequest;
import com.nexastay.roomservice.request.UpdateRoomRequest;
import com.nexastay.roomservice.service.interfaces.IRoomService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService{
    @Autowired
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    @Override
    public Room getRoomById(UUID id) {
        return roomRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Room not found!"));
    }

    @Override
    public Room updateRoomById(UpdateRoomRequest room, UUID id) {
        return roomRepository.findById(id)
                .map(existingRoom -> updateExistingRoom(existingRoom, room))
                .map(roomRepository :: save)
                .orElseThrow(()-> new ResourceNotFoundException("Room not found!"));
    }

    private Room updateExistingRoom(Room existingRoom, UpdateRoomRequest request){
        existingRoom.setNumber(request.getNumber());
        existingRoom.setDescription(request.getDescription());
        existingRoom.setCapacity(request.getCapacity());
        existingRoom.setPrice(request.getPrice());
        existingRoom.setType(RoomType.valueOf(request.getType().toUpperCase()));
        existingRoom.setStatus(RoomStatus.valueOf(request.getStatus().toUpperCase()));
        existingRoom.setImageUrl(request.getImageUrl());

        return existingRoom;
    }

    @Override
    public Room getRoombyNumber(String number) {
        return roomRepository.findByNumber(number);
    }

    @Override
    public void deleteRoomById(UUID id) {
        roomRepository.findById(id)
            .ifPresentOrElse(roomRepository::delete,
            ()->{throw new ResourceNotFoundException("Room not found !");
        });
    }

    @Override
    public List<Room> getAllRoom() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> getRoomByCapacity(Integer capacity) {
        return roomRepository.findByCapacity(capacity);
    }

    @Override
    public List<Room> getRoomByType(String type) {
        return roomRepository.findByType(type);
    }

    @Override
    public List<Room> getRoomByStatus(String status) {
        return roomRepository.findByStatus(status);
    }

    @Override
    public List<Room> getRoomByPrice(BigDecimal price) {
        return roomRepository.findByPrice(price);
    }

    @Override
    public Room addRoom(AddRoomRequest room) {
        return roomRepository.save(createRoom(room));
    }

    //Before adding a  room, we need to create it
    private Room createRoom(AddRoomRequest request){
        return new Room(
            request.getNumber(),
            request.getCapacity(),
            request.getDescription(),
            request.getImageUrl(),
            request.getPrice(),
                RoomStatus.valueOf(request.getStatus().toUpperCase()),
                RoomType.valueOf(request.getType().toUpperCase())
        );
    }

    @Override
    public RoomDto convertRoomToDto(Room room){
        RoomDto roomsDto = new RoomDto();
        roomsDto = modelMapper.map(room, RoomDto.class);
        return roomsDto;
    }
}
