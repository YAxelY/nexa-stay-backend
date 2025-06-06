package com.nexastay.reviewservice.service;

import com.nexastay.reviewservice.dto.ReviewRequestDTO;
import com.nexastay.reviewservice.dto.ReviewResponseDTO;
import com.nexastay.reviewservice.exception.DuplicateReviewException;
import com.nexastay.reviewservice.exception.UnauthorizedException;
import com.nexastay.reviewservice.mapper.ReviewMapper;
import com.nexastay.reviewservice.model.Review;
import com.nexastay.reviewservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO) {
        log.info("Creating new review for user {} and room {}",
                reviewRequestDTO.getUserId(), reviewRequestDTO.getRoomId());

        try {
            // Get the authenticated user ID from the security context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                log.error("No authentication found in SecurityContext");
                throw new AccessDeniedException("Authentication required");
            }

            String authenticatedUserId = authentication.getName();
            if (authenticatedUserId == null) {
                log.error("No user ID found in authentication");
                throw new AccessDeniedException("User ID not found in authentication");
            }

            log.info("Authentication context: principal={}, name={}, authorities={}, details={}",
                    authentication.getPrincipal(),
                    authenticatedUserId,
                    authentication.getAuthorities(),
                    authentication.getDetails());

            // Validate request data
            if (reviewRequestDTO.getUserId() == null) {
                log.error("Review request missing userId");
                throw new IllegalArgumentException("User ID is required");
            }

            if (reviewRequestDTO.getRoomId() == null) {
                log.error("Review request missing roomId");
                throw new IllegalArgumentException("Room ID is required");
            }

            // Validate that the user ID in the request matches the authenticated user
            if (!reviewRequestDTO.getUserId().toString().equals(authenticatedUserId)) {
                log.error("User ID mismatch: request={}, authenticated={}",
                        reviewRequestDTO.getUserId(), authenticatedUserId);
                throw new AccessDeniedException("You can only submit reviews as yourself");
            }

            // Check if user has already reviewed this room
            if (reviewRepository.existsByUserIdAndRoomId(reviewRequestDTO.getUserId(), reviewRequestDTO.getRoomId())) {
                log.warn("User {} has already reviewed room {}",
                        reviewRequestDTO.getUserId(), reviewRequestDTO.getRoomId());
                throw new DuplicateReviewException("User has already reviewed this room");
            }

            Review review = reviewMapper.toEntity(reviewRequestDTO);
            review.setReviewDate(LocalDate.now());

            log.debug("Saving review with auth context - userId: {}, authenticatedId: {}, review: {}",
                    reviewRequestDTO.getUserId(), authenticatedUserId, review);
            Review savedReview = reviewRepository.save(review);
            log.info("Successfully saved review with ID: {} for authenticated user: {}",
                    savedReview.getId(), authenticatedUserId);

            return reviewMapper.toDto(savedReview);
        } catch (Exception e) {
            log.error("Error creating review: {} - Auth context: user={}",
                    e.getMessage(),
                    SecurityContextHolder.getContext().getAuthentication() != null
                            ? SecurityContextHolder.getContext().getAuthentication().getName()
                            : "no auth",
                    e);
            throw e;
        }
    }

    public List<ReviewResponseDTO> getLatestReviews() {
        log.info("Fetching latest 6 reviews");
        PageRequest pageRequest = PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "reviewDate"));
        List<Review> reviews = reviewRepository.findAll(pageRequest).getContent();
        log.info("Found {} latest reviews", reviews.size());
        return reviewMapper.toDtoList(reviews);
    }

    public void deleteReview(Long reviewId, Long userId) {
        log.info("Attempting to delete review {} by user {}", reviewId, userId);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    log.error("Review not found with ID: {}", reviewId);
                    return new RuntimeException("Review not found");
                });

        if (!review.getUserId().equals(userId)) {
            log.warn("User {} attempted to delete review {} owned by user {}",
                    userId, reviewId, review.getUserId());
            throw new UnauthorizedException("You are not authorized to delete this review");
        }

        reviewRepository.delete(review);
        log.info("Successfully deleted review {}", reviewId);
    }

    public List<ReviewResponseDTO> getReviewsByRoom(Long roomId) {
        log.info("Fetching reviews for room {}", roomId);
        List<Review> reviews = reviewRepository.findByRoomId(roomId);
        log.info("Found {} reviews for room {}", reviews.size(), roomId);
        return reviewMapper.toDtoList(reviews);
    }

    public Double getAverageRatingForRoom(Long roomId) {
        log.info("Calculating average rating for room {}", roomId);
        Double average = reviewRepository.findAverageRatingByRoomId(roomId);
        log.info("Average rating for room {}: {}", roomId, average);
        return average;
    }

    public List<ReviewResponseDTO> getReviewsByUser(Long userId) {
        log.info("Fetching reviews by user {}", userId);
        List<Review> reviews = reviewRepository.findByUserId(userId);
        log.info("Found {} reviews for user {}", reviews.size(), userId);
        return reviewMapper.toDtoList(reviews);
    }
}
