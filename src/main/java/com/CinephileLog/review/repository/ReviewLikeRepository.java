package com.CinephileLog.review.repository;

import com.CinephileLog.domain.User;
import com.CinephileLog.review.domain.Review;
import com.CinephileLog.review.domain.ReviewLike;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    Optional<ReviewLike> findByUserAndReview(User user, Review review);
    boolean existsByUserAndReview(User user, Review review);
    void deleteByUserAndReview(User user, Review review);
    @Query("SELECT COUNT(rl) FROM ReviewLike rl WHERE rl.review.user.userId = :userId")
    int countByUserId(Long userId);
    List<ReviewLike> findByUserUserId(Long userId);
}
