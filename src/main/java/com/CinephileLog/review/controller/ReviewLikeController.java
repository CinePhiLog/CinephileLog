package com.CinephileLog.review.controller;

import com.CinephileLog.domain.User;
import com.CinephileLog.review.domain.Review;
import com.CinephileLog.review.dto.ReviewLikeResponse;
import com.CinephileLog.review.repository.ReviewRepository;
import com.CinephileLog.review.service.ReviewLikeService;
import java.util.NoSuchElementException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewLikeController {
    private final ReviewRepository reviewRepository;
    private final ReviewLikeService reviewLikeService;

    public ReviewLikeController(ReviewLikeService reviewLikeService, ReviewRepository reviewRepository) {
        this.reviewLikeService = reviewLikeService;
        this.reviewRepository = reviewRepository;
    }

    @PostMapping("/reviews/{reviewId}/like")
    public ResponseEntity<ReviewLikeResponse> reviewLike(@PathVariable Long reviewId,
                                                         @AuthenticationPrincipal OAuth2User oAuth2User) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 리뷰가 없음"));

        Long userId = oAuth2User.getAttribute("userId");
        boolean liked = reviewLikeService.reviewLike(userId, review);

        return ResponseEntity.ok(new ReviewLikeResponse(liked));
    }
}
