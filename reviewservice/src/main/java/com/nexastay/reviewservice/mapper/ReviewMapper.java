package com.nexastay.reviewservice.mapper;

import com.nexastay.reviewservice.dto.ReviewRequestDTO;
import com.nexastay.reviewservice.dto.ReviewResponseDTO;
import com.nexastay.reviewservice.model.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

    public Review toEntity(ReviewRequestDTO dto) {
        return Review.builder()
                .userId(dto.getUserId())
                .roomId(dto.getRoomId())
                .rating(dto.getRating())
                .comment(dto.getComment())
                .reviewDate(LocalDate.now())
                .build();
    }

    public ReviewResponseDTO toDto(Review review) {
        return ReviewResponseDTO.builder()
                .id(review.getId())
                .userId(review.getUserId())
                .roomId(review.getRoomId())
                .rating(review.getRating())
                .comment(review.getComment())
                .reviewDate(review.getReviewDate())
                .build();
    }

    public List<ReviewResponseDTO> toDtoList(List<Review> reviews) {
        return reviews.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
