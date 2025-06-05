package com.nexastay.reviewservice.controller;

import com.nexastay.reviewservice.dto.ReviewRequestDTO;
import com.nexastay.reviewservice.dto.ReviewResponseDTO;
import com.nexastay.reviewservice.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> createReview(
            @Valid @RequestBody ReviewRequestDTO reviewDTO) {
        log.info("Received review submission request: userId={}, roomId={}, rating={}",
                reviewDTO.getUserId(), reviewDTO.getRoomId(), reviewDTO.getRating());
        try {
            // Get authenticated user ID from security context
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            log.debug("Authentication details: principal={}, authorities={}, details={}",
                    auth.getPrincipal(), auth.getAuthorities(), auth.getDetails());

            Long authenticatedUserId = Long.parseLong(auth.getName());
            log.info("Authenticated user ID: {}", authenticatedUserId);

            // Validate that the user ID in the request matches the authenticated user
            if (!reviewDTO.getUserId().equals(authenticatedUserId)) {
                log.error("User ID mismatch: request={}, authenticated={}",
                        reviewDTO.getUserId(), authenticatedUserId);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            ReviewResponseDTO createdReview = reviewService.createReview(reviewDTO);
            log.info("Successfully created review with id: {}", createdReview.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
        } catch (Exception e) {
            log.error("Error creating review: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/latest")
    public ResponseEntity<List<ReviewResponseDTO>> getLatestReviews() {
        log.info("Fetching latest reviews");
        try {
            List<ReviewResponseDTO> reviews = reviewService.getLatestReviews();
            log.info("Found {} latest reviews", reviews.size());
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            log.error("Error fetching latest reviews: {}", e.getMessage(), e);
            throw e;
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            @RequestParam Long userId) {
        log.info("Attempting to delete review: reviewId={}, userId={}", reviewId, userId);
        try {
            reviewService.deleteReview(reviewId, userId);
            log.info("Successfully deleted review: {}", reviewId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting review: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<ReviewResponseDTO>> getRoomReviews(
            @PathVariable Long roomId) {
        log.info("Fetching reviews for room: {}", roomId);
        try {
            List<ReviewResponseDTO> reviews = reviewService.getReviewsByRoom(roomId);
            log.info("Found {} reviews for room {}", reviews.size(), roomId);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            log.error("Error fetching room reviews: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/room/{roomId}/average")
    public ResponseEntity<Double> getRoomAverageRating(
            @PathVariable Long roomId) {
        log.info("Calculating average rating for room: {}", roomId);
        try {
            Double average = reviewService.getAverageRatingForRoom(roomId);
            log.info("Average rating for room {}: {}", roomId, average);
            return ResponseEntity.ok(average);
        } catch (Exception e) {
            log.error("Error calculating room average rating: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponseDTO>> getUserReviews(
            @PathVariable Long userId) {
        log.info("Fetching reviews for user: {}", userId);
        try {
            List<ReviewResponseDTO> reviews = reviewService.getReviewsByUser(userId);
            log.info("Found {} reviews for user {}", reviews.size(), userId);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            log.error("Error fetching user reviews: {}", e.getMessage(), e);
            throw e;
        }
    }
}
