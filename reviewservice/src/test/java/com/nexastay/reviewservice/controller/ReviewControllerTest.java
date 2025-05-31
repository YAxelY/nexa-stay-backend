package com.nexastay.reviewservice.controller;

import com.nexastay.reviewservice.dto.ReviewRequestDTO;
import com.nexastay.reviewservice.mapper.ReviewMapper;
import com.nexastay.reviewservice.model.Review;
import com.nexastay.reviewservice.repository.ReviewRepository;
import com.nexastay.reviewservice.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ReviewController.class)
@Import({ReviewService.class, ReviewMapper.class})
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReviewRepository reviewRepository;

    @Test
    void shouldCreateReview() throws Exception {
        ReviewRequestDTO request = new ReviewRequestDTO(1L, 1L, 5, "Excellent!");

        when(reviewRepository.existsByUserIdAndRoomId(1L, 1L)).thenReturn(false);
        when(reviewRepository.save(any())).thenReturn(
                Review.builder().id(1L).userId(1L).roomId(1L).rating(5)
                        .comment("Excellent!").reviewDate(LocalDate.now()).build());

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "userId": 1,
                            "roomId": 1,
                            "rating": 5,
                            "comment": "Excellent!"
                        }"""))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

}
