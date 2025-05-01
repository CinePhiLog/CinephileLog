package com.CinephileLog.repository;

import com.CinephileLog.domain.Review;
import com.CinephileLog.dto.ReviewListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT new com.CinephileLog.dto.ReviewListDTO(r.reviewId, u.nickname, r.content, r.createdDate) " +     // db에서 조회한 결과를 ReviewListDTO 객체로 생성
            "FROM Review r JOIN r.user u JOIN r.movieId m " +
            "WHERE (:keyword IS NULL OR u.nickname LIKE %:keyword% OR r.content LIKE %:keyword% OR m.title LIKE %:keyword%) " +
            "ORDER BY r.createdDate DESC")      // 최신순으로 정렬
    List<ReviewListDTO> findAllReviewsWithNickname(@Param("keyword") String keyword);       // 리뷰 목록이랑 같이 작성자 닉네임 조회
}
