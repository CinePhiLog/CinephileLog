package com.CinephileLog.review.repository;

import com.CinephileLog.review.domain.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByUser_UserIdAndMovie_id(Long userId, Long movieId);
    List<Review> findAllByMovie_Id(Long movieId);
    List<Review> findAllByUser_UserId(Long userId);
    Review findByUser_UserIdAndMovie_id(Long userId, Long movieId);
    Review findByMovie_IdAndUser_Nickname(Long movieId, String nickname);
    List<Review> findAllByUser_Nickname(String nickname);
    int countByUserUserId(Long userId);
}
