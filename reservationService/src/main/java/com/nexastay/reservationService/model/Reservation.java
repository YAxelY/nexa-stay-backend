package com.nexastay.reservationService.model;

import java.time.LocalDate;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.nexastay.reservationService.enums.ReservationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private ReservationStatus reservationStatus;
    private Long userId;
    private Long roomId;

    public void cancel() {
        if (this.reservationStatus == ReservationStatus.REJECTED) {
            throw new IllegalStateException("L");
        }
        this.reservationStatus = ReservationStatus.REJECTED;
    }

    @Embeddable
    public static class AuditMetadata {
        @Column(name = "created_at", updatable = false)
        @CreatedDate
         private LocalDate createdAt;

        @Column(name = "updated_at")
        @LastModifiedBy
         @LastModifiedDate
        private LocalDate updatedAt;
                                    
        // Getters
    }

}
