package com.nexastay.reservationservice.mapper;

import com.nexastay.reservationservice.dto.ReservationDto;
import com.nexastay.reservationservice.model.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
    public ReservationDto toDto(Reservation reservation) {
        return ReservationDto.builder()
                .id(reservation.getId())
                .userId(reservation.getUserId())
                .roomId(reservation.getRoomId())
                .checkInDate(reservation.getCheckInDate())
                .checkOutDate(reservation.getCheckOutDate())
                .status(reservation.getStatus())
                .totalPrice(reservation.getTotalPrice())
                .numberOfGuests(reservation.getNumberOfGuests())
                .createdAt(reservation.getCreatedAt())
                .updatedAt(reservation.getUpdatedAt())
                .build();
    }
}
