package com.nexastay.reservationService.response;

import java.time.LocalDate;
import java.util.UUID;

import com.nexastay.reservationService.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {
    private Long id;
    private Long userId;
    private Long roomId;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private ReservationStatus reservationStatus;
}

