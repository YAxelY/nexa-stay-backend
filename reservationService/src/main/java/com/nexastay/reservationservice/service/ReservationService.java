package com.nexastay.reservationservice.service;

import com.nexastay.reservationservice.dto.CreateReservationRequest;
import com.nexastay.reservationservice.dto.ReservationResponse;
import com.nexastay.reservationservice.enums.ReservationStatus;
import com.nexastay.reservationservice.model.Reservation;
import com.nexastay.reservationservice.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final WebClient.Builder webClientBuilder;

    @Transactional
    public ReservationResponse createReservation(CreateReservationRequest request) {
        // Check if room is available for the requested dates
        List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(
                request.getRoomId(),
                request.getCheckInDate(),
                request.getCheckOutDate());

        if (!overlappingReservations.isEmpty()) {
            throw new IllegalStateException("Room is not available for the selected dates");
        }

        // Get room price from room service
        BigDecimal roomPrice = getRoomPrice(request.getRoomId());

        // Calculate total price based on number of nights
        long numberOfNights = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
        BigDecimal totalPrice = roomPrice.multiply(BigDecimal.valueOf(numberOfNights));

        // Create and save the reservation
        Reservation reservation = Reservation.builder()
                .userId(request.getUserId())
                .roomId(request.getRoomId())
                .checkInDate(request.getCheckInDate())
                .checkOutDate(request.getCheckOutDate())
                .status(ReservationStatus.PENDING)
                .totalPrice(totalPrice)
                .numberOfGuests(request.getNumberOfGuests())
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);
        return mapToReservationResponse(savedReservation);
    }

    public List<ReservationResponse> getReservationsByUserId(UUID userId) {
        return reservationRepository.findByUserId(userId).stream()
                .map(this::mapToReservationResponse)
                .toList();
    }

    public long getReservationCountByUserId(UUID userId) {
        return reservationRepository.countReservationsByUserId(userId);
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::mapToReservationResponse)
                .toList();
    }

    private BigDecimal getRoomPrice(UUID roomId) {
        try {
            return webClientBuilder.build().get()
                    .uri("http://roomservice/api/rooms/" + roomId + "/price")
                    .retrieve()
                    .bodyToMono(BigDecimal.class)
                    .block();
        } catch (Exception e) {
            log.error("Error fetching room price: {}", e.getMessage());
            throw new IllegalStateException("Could not fetch room price");
        }
    }

    private ReservationResponse mapToReservationResponse(Reservation reservation) {
        return ReservationResponse.builder()
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
