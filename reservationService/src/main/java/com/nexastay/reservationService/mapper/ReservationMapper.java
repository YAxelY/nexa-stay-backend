package com.nexastay.reservationService.mapper;

import org.springframework.stereotype.Component;

import com.nexastay.reservationService.model.Reservation;
import com.nexastay.reservationService.response.ReservationResponse;

@Component
public class ReservationMapper {
    public ReservationResponse toReservationResponse(Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getUserId(),
            reservation.getRoomId(),
            reservation.getDateDebut(),
            reservation.getDateFin(),
            reservation.getReservationStatus()
        );
    }
}
