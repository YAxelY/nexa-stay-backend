package com.nexastay.reviewservice.service;

import com.nexastay.reviewservice.dto.ReviewRequestDTO;
import com.nexastay.reviewservice.dto.ReviewResponseDTO;
import com.nexastay.reviewservice.exception.DuplicateReviewException;
import com.nexastay.reviewservice.mapper.ReviewMapper;
import com.nexastay.reviewservice.model.Review;
import com.nexastay.reviewservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO) {
        // Vérifier si l'utilisateur a déjà noté cette chambre
        if (reviewRepository.existsByUserIdAndRoomId(reviewRequestDTO.getUserId(), reviewRequestDTO.getRoomId())) {
            throw new DuplicateReviewException("User has already reviewed this room");
        }

        Review review = reviewMapper.toEntity(reviewRequestDTO);
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDto(savedReview);
    }

    public List<ReviewResponseDTO> getReviewsByRoom(Long roomId) {
        List<Review> reviews = reviewRepository.findByRoomId(roomId);
        return reviewMapper.toDtoList(reviews);
    }

    public Double getAverageRatingForRoom(Long roomId) {
        return reviewRepository.findAverageRatingByRoomId(roomId);
    }

    public List<ReviewResponseDTO> getReviewsByUser(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        return reviewMapper.toDtoList(reviews);
    }
}
