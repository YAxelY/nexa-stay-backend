package com.nexastay.roomservice.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexastay.roomservice.model.Room;


public interface RoomRepository extends JpaRepository<Room, UUID>{

    Room findByNumber(String number);

    List<Room> findByCapacity(Integer capacity);

	List<Room> findByType(String type);

    List<Room> findByStatus(String status);

    List<Room> findByPrice(BigDecimal price);

}
