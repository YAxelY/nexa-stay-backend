package com.nexastay.reviewservice.controller;

import com.nexastay.reviewservice.dto.ReviewRequestDTO;
import com.nexastay.reviewservice.dto.ReviewResponseDTO;
import com.nexastay.reviewservice.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> createReview(
            @Valid @RequestBody ReviewRequestDTO reviewDTO) {
        ReviewResponseDTO createdReview = reviewService.createReview(reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<ReviewResponseDTO>> getRoomReviews(
            @PathVariable Long roomId) {
        return ResponseEntity.ok(reviewService.getReviewsByRoom(roomId));
    }

    @GetMapping("/room/{roomId}/average")
    public ResponseEntity<Double> getRoomAverageRating(
            @PathVariable Long roomId) {
        return ResponseEntity.ok(reviewService.getAverageRatingForRoom(roomId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponseDTO>> getUserReviews(
            @PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }
}
