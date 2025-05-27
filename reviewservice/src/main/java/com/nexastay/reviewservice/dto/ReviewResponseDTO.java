package com.nexastay.reviewservice.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDTO {
    private Long id;
    private Long userId;
    private Long roomId;
    private int rating;
    private String comment;
    private LocalDate reviewDate;
}