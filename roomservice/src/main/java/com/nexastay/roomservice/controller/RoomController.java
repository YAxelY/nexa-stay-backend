package com.nexastay.roomservice.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.nexastay.roomservice.dto.RoomDto;
import com.nexastay.roomservice.mapper.RoomMapper;
import com.nexastay.roomservice.model.enums.RoomType;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nexastay.roomservice.exceptions.ResourceNotFoundException;
import com.nexastay.roomservice.model.Room;
import com.nexastay.roomservice.request.AddRoomRequest;
import com.nexastay.roomservice.request.UpdateRoomRequest;
import com.nexastay.roomservice.response.ApiResponse;
import com.nexastay.roomservice.service.RoomService;
import com.nexastay.roomservice.service.FileStorageService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private final RoomService roomService;
    private final RoomMapper roomMapper;
    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllRooms() {
        List<Room> rooms = roomService.getAllRoom();
        List<RoomDto> roomDtos = roomMapper.convertRoomToListDto(rooms);
        return ResponseEntity.ok(new ApiResponse("Success", roomDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findRoomById(@PathVariable UUID id) {
        try {
            Room room = roomService.getRoomById(id);
            RoomDto roomDto = roomMapper.convertRoomToDto(room);
            return ResponseEntity.ok(new ApiResponse("Success", roomDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Room not found!", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addRoom(
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("description") String description,
            @RequestParam("price") BigDecimal price,
            @RequestParam("capacity") Integer capacity,
            @RequestParam("status") String status,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "imageUrl", required = false) String imageUrl) {
        try {
            AddRoomRequest room = new AddRoomRequest();
            room.setName(name);
            room.setType(type);
            room.setDescription(description);
            room.setPrice(price);
            room.setCapacity(capacity);
            room.setStatus(status);

            // Handle image upload if present
            if (image != null && !image.isEmpty()) {
                String filename = fileStorageService.storeRoomImage(image);
                room.setImageUrl(fileStorageService.getImageUrl(filename));
            } else if (imageUrl != null && !imageUrl.isEmpty()) {
                room.setImageUrl(imageUrl);
            }

            // Ensure type is a valid RoomType
            try {
                RoomType.valueOf(room.getType().toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse("Invalid room type. Valid types are: " +
                                Arrays.toString(RoomType.values()), null));
            }

            Room newRoom = roomService.addRoom(room);
            RoomDto newRoomDto = roomMapper.convertRoomToDto(newRoom);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Success ✅, room added successfully", newRoomDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateRoom(@RequestBody UpdateRoomRequest room,
            @PathVariable UUID id) {
        try {
            Room updatedRoom = roomService.updateRoomById(room, id);
            RoomDto updatedRoomDto = roomMapper.convertRoomToDto(updatedRoom);
            return ResponseEntity.ok(new ApiResponse("Success", updatedRoomDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse> findRoomByName(@PathVariable String name) {
        try {
            Room room = roomService.getRoomByName(name);
            RoomDto roomDto = roomMapper.convertRoomToDto(room);
            if (room == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No room found with the name " + name, null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", roomDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/capacity/{capacity}")
    public ResponseEntity<ApiResponse> findRoomByCapacity(@PathVariable Integer capacity) {
        try {
            List<Room> rooms = roomService.getRoomByCapacity(capacity);
            List<RoomDto> roomDtos = roomMapper.convertRoomToListDto(rooms);
            if (roomDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No room found with the capacity " + capacity, null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", roomDtos));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/price/{price}")
    public ResponseEntity<ApiResponse> findRoomByPrice(@PathVariable BigDecimal price) {
        try {
            List<Room> rooms = roomService.getRoomByPrice(price);
            List<RoomDto> roomDtos = roomMapper.convertRoomToListDto(rooms);
            if (roomDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No room found with the price " + price, null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", roomDtos));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse> findRoomByType(@PathVariable String type) {
        try {
            List<Room> rooms = roomService.getRoomByType(type);
            List<RoomDto> roomDtos = roomMapper.convertRoomToListDto(rooms);
            if (roomDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No room found for the type" + type, null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", roomDtos));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRoomById(@PathVariable UUID id) {
        try {
            roomService.deleteRoomById(id);
            return ResponseEntity.ok(new ApiResponse("Success", "Room deleted successfully"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Room not found!", e.getMessage()));
        }
    }
}
