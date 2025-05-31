package com.nexastay.reviewservice.dto;


import java.time.LocalDate;


public record ReviewDTO(
        Long id,
        Long userId,
        Long roomId,
        int rating,
        String comment,
        LocalDate reviewDate
) {
}
