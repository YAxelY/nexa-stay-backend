package com.nexastay.reservationService.service;


import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Service;

import com.nexastay.reservationService.dto.ReservationDto;
import com.nexastay.reservationService.enums.ReservationStatus;
import com.nexastay.reservationService.model.Reservation;
import com.nexastay.reservationService.repository.ReservationRepository;
import com.nexastay.reservationService.exception.ReservationException;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ReservationService {

    private final ReservationRepository reservationRepository;

    public boolean postReservation(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        reservation.setUserId(reservationDto.getUserId());
        reservation.setRoomId(reservationDto.getRoomId());
        reservation.setDateDebut(reservationDto.getDateDebut());
        reservation.setDateFin(reservationDto.getDateFin());
        reservation.setReservationStatus(ReservationStatus.PENDING);
        reservationRepository.save(reservation);
        return true;
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new ReservationException("Reservation not found"));
    }

    public String cancelReservation(Long id) {
        Reservation reservation = getReservationById(id);
        reservation.setReservationStatus(ReservationStatus.REJECTED);
        reservationRepository.save(reservation);
        return "Reservation rejected successfully";
    }
}

