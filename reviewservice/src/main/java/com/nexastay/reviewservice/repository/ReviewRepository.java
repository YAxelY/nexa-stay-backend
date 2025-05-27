package com.nexastay.reviewservice.repository;

import com.nexastay.reviewservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByRoomId(Long roomId);
    List<Review> findByUserId(Long userId);
    boolean existsByUserIdAndRoomId(Long userId, Long roomId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.roomId = :roomId")
    Double findAverageRatingByRoomId(@Param("roomId") Long roomId);
}
