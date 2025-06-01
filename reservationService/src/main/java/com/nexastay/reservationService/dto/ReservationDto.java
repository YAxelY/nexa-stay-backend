package com.nexastay.reservationService.dto;

import java.time.LocalDate;

import com.nexastay.reservationService.enums.ReservationStatus;

import lombok.Data;

@Data
public class ReservationDto {
    private Long id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private ReservationStatus reservationStatus;
    
    private Long roomId;
    private Long userId;

}
