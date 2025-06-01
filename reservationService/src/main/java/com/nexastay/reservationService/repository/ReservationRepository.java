package com.nexastay.reservationService.repository;

import org.springframework.stereotype.Repository;
import com.nexastay.reservationService.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByRoomId(Long roomId);
    boolean existsByUserIdAndRoomId(Long userId, Long roomId);

}
