package com.nexastay.reservationservice.dto;

import com.nexastay.reservationservice.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private UUID id;
    private UUID userId;
    private UUID roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private ReservationStatus status;
    private BigDecimal totalPrice;
    private Integer numberOfGuests;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
