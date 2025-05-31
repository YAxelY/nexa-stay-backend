package com.nexastay.reviewservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private Long userId;


    @Column(nullable = false)
    private Long roomId;

    @Column(nullable = false)
    @Min(1) @Max(5)
    private int rating;

    private String comment;

    @Column(nullable = false)
    private LocalDate reviewDate;
}