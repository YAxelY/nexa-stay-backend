package com.nexastay.reviewservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexastay.reviewservice.config.TestSecurityConfig;
import com.nexastay.reviewservice.dto.ReviewRequestDTO;
import com.nexastay.reviewservice.mapper.ReviewMapper;
import com.nexastay.reviewservice.model.Review;
import com.nexastay.reviewservice.repository.ReviewRepository;
import com.nexastay.reviewservice.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@WebMvcTest(ReviewController.class)
@Import({ ReviewService.class, ReviewMapper.class, TestSecurityConfig.class })
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewRepository reviewRepository;

    private ReviewRequestDTO reviewRequest;
    private Review mockReview;

    @BeforeEach
    void setUp() {
        reviewRequest = new ReviewRequestDTO();
        reviewRequest.setUserId(1L);
        reviewRequest.setRoomId(1L);
        reviewRequest.setRating(5);
        reviewRequest.setComment("Excellent!");
        reviewRequest.setPhoto("base64EncodedPhoto");

        mockReview = Review.builder()
                .id(1L)
                .userId(1L)
                .roomId(1L)
                .rating(5)
                .comment("Excellent!")
                .photo("base64EncodedPhoto")
                .reviewDate(LocalDate.now())
                .build();
    }

    @Test
    void shouldCreateReview() throws Exception {
        when(reviewRepository.existsByUserIdAndRoomId(1L, 1L)).thenReturn(false);
        when(reviewRepository.save(any())).thenReturn(mockReview);

        mockMvc.perform(post("/api/reviews")
                .with(jwt()
                        .jwt(jwt -> jwt
                                .claim("userId", 1L)
                                .claim("role", "CLIENT")
                                .claim("email", "test@example.com")
                                .subject("test@example.com")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.photo").exists());
    }

    @Test
    void shouldRejectUnauthenticatedRequest() throws Exception {
        mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewRequest)))
                .andExpect(status().isUnauthorized());
    }
}
