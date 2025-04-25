package com.nexastay.roomservice.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.nexastay.roomservice.dto.RoomDto;
import com.nexastay.roomservice.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexastay.roomservice.exceptions.ResourceNotFoundException;
import com.nexastay.roomservice.model.Room;
import com.nexastay.roomservice.request.AddRoomRequest;
import com.nexastay.roomservice.request.UpdateRoomRequest;
import com.nexastay.roomservice.response.ApiResponse;
import com.nexastay.roomservice.service.RoomService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private final RoomService roomService;
    private final RoomMapper roomMapper;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllRoom(){
        List<Room> rooms = roomService.getAllRoom();
        List<RoomDto> roomDtos = roomMapper.convertRoomToListDto(rooms);
        return ResponseEntity.ok(new ApiResponse("Success", roomDtos));
    }

    @GetMapping("/room/{id}/room")
    public ResponseEntity<ApiResponse> findRoomById(@PathVariable UUID id){
        try {
            Room room = roomService.getRoomById(id);
            RoomDto roomDto = roomMapper.convertRoomToDto(room);
            return ResponseEntity.ok(new ApiResponse("Success", roomDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Room not found!", e.getMessage()));
        }

    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addRoom(@RequestBody AddRoomRequest room){
        try {
            Room newRoom = roomService.addRoom(room);
            RoomDto newRoomDto = roomMapper.convertRoomToDto(newRoom);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Success ✅, room added successfully", newRoomDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/room/{id}/update")
    public ResponseEntity<ApiResponse> updateResponseEntity(@RequestBody UpdateRoomRequest room, @PathVariable UUID id){
        try{
            Room updatedRoom = roomService.updateRoomById(room, id);
            RoomDto updatedRoomDto = roomMapper.convertRoomToDto(updatedRoom);
            return ResponseEntity.ok(new ApiResponse("Success", updatedRoomDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/room/number/{number}")
    public ResponseEntity<ApiResponse> findRoomByNumber(@PathVariable String number){
        try {
            Room room = roomService.getRoombyNumber(number);
            RoomDto roomDto = roomMapper.convertRoomToDto(room);
            if (room == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No room found with the number "+number, null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", roomDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @GetMapping("/room/capacity/{capacity}")
    public ResponseEntity<ApiResponse> findRoomByCapacity(@PathVariable Integer capacity){
        try {
            List<Room> rooms = roomService.getRoomByCapacity(capacity);
            List<RoomDto> roomDtos = roomMapper.convertRoomToListDto(rooms);
            if (roomDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No room found with the capacity "+capacity, null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", roomDtos));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @GetMapping("/room/price/{price}")
    public ResponseEntity<ApiResponse> findRoomByPrice(@PathVariable BigDecimal price){
        try {
            List<Room> rooms = roomService.getRoomByPrice(price);
            List<RoomDto> roomDtos = roomMapper.convertRoomToListDto(rooms);
            if (roomDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No room found with the price "+price, null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", roomDtos));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @GetMapping("/room/type/{type}")
    public ResponseEntity<ApiResponse> findRoomByType(@PathVariable String type){
        try {
            List<Room> rooms = roomService.getRoomByType(type);
            List<RoomDto> roomDtos = roomMapper.convertRoomToListDto(rooms);
            if (roomDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No room found for the type" + type, null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", roomDtos));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/room/{id}/delete")
    public ResponseEntity<ApiResponse> deleteRoomById(@PathVariable UUID id){
        try {
            roomService.deleteRoomById(id);
            return ResponseEntity.ok(new ApiResponse("Success", "Room deleted successfully"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Room not found!", e.getMessage()));
        }
    }
}
