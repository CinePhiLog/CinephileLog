package com.CinephileLog.review.dto;

import com.CinephileLog.dto.UserResponse;
import com.CinephileLog.movie.dto.MovieResponse;
import com.CinephileLog.review.domain.Review;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private MovieResponse movie;
    private UserResponse user;
    private BigDecimal rating;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Long likeCount;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.movie = new MovieResponse(review.getMovie());
        this.user = new UserResponse(review.getUser());
        this.rating = review.getRating();
        this.content = review.getContent();
        this.createdDate = review.getCreatedDate();
        this.updatedDate = review.getUpdatedDate();
        this.likeCount = review.getLikeCount();
    }
}
