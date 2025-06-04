package com.nexastay.reservationservice.repository;

import com.nexastay.reservationservice.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findByUserId(UUID userId);

    @Query("SELECT r FROM Reservation r WHERE r.roomId = :roomId AND " +
            "((r.checkInDate <= :checkOut AND r.checkOutDate >= :checkIn) OR " +
            "(r.checkInDate >= :checkIn AND r.checkInDate < :checkOut))")
    List<Reservation> findOverlappingReservations(
            @Param("roomId") UUID roomId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut);

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.userId = :userId")
    long countReservationsByUserId(@Param("userId") UUID userId);
}
