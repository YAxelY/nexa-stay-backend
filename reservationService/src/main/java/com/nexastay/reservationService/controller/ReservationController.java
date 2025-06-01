package com.nexastay.reservationService.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexastay.reservationService.dto.ReservationDto;
import com.nexastay.reservationService.exception.ReservationException;
import com.nexastay.reservationService.mapper.ReservationMapper;
import com.nexastay.reservationService.model.Reservation;
import com.nexastay.reservationService.response.ApiResponse;
import com.nexastay.reservationService.response.ReservationResponse;
import com.nexastay.reservationService.service.ReservationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;
 
    @PostMapping("/reservation")
    public ResponseEntity<ApiResponse> postReservation(@RequestBody ReservationDto reservationDto){
        boolean success = reservationService.postReservation(reservationDto);

        if(success){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getReservationById(@PathVariable Long id){
        try{
            Reservation reservation = reservationService.getReservationById(id);
            ReservationResponse response = reservationMapper.toReservationResponse(reservation);
            return ResponseEntity.ok(new ApiResponse("Success ✅", response));
        } catch (ReservationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error ❌", e.getMessage())); 
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse> cancelReservation(@PathVariable Long id){
        try {
            reservationService.cancelReservation(id);
            return ResponseEntity.ok(new ApiResponse("Success ✅", "Reservation cancelled"));
        } catch (ReservationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error ❌", e.getMessage()));
        }
    } 

}

