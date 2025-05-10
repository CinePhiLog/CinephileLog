package com.CinephileLog.review.repository;

import com.CinephileLog.review.domain.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByUser_UserIdAndMovie_id(Long userId, Long movieId);
    List<Review> findAllByMovie_Id(Long movieId);
    List<Review> findAllByUser_UserId(Long userId);
    Review findByUser_UserIdAndMovie_id(Long userId, Long movieId);
    Review findByMovie_IdAndUser_Nickname(Long movieId, String nickname);
    List<Review> findAllByUser_Nickname(String nickname);
    int countByUserUserId(Long userId);

    // 키워드로 검색
    @Query("SELECT r FROM Review r " +
            "WHERE (:keyword IS NULL OR LOWER(r.user.nickname) LIKE LOWER(concat('%', :keyword, '%'))) " +
            "OR (:keyword IS NULL OR LOWER(r.content) LIKE LOWER(concat('%', :keyword, '%'))) " +
            "OR (:keyword IS NULL OR LOWER(r.movie.title) LIKE LOWER(concat('%', :keyword, '%')))")
    List<Review> searchReviews(@Param("keyword") String keyword);

    //Get top 3 movie id based on review count
    @Query(value = "SELECT r.movie_id FROM review r " +
            "GROUP BY r.movie_id " +
            "ORDER BY MAX(r.like_count) DESC " +
            "LIMIT 3", nativeQuery = true)
    List<Long> findTop3MovieIdsByReview();

}
