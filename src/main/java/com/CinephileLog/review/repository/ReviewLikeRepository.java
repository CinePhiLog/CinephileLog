package com.CinephileLog.review.repository;

import com.CinephileLog.domain.User;
import com.CinephileLog.review.domain.Review;
import com.CinephileLog.review.domain.ReviewLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    Optional<ReviewLike> findByUserAndReview(User user, Review review);
    boolean existsByUserAndReview(User user, Review review);
    void deleteByUserAndReview(User user, Review review);
}
