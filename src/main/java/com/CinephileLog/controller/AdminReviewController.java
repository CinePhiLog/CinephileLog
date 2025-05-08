package com.CinephileLog.controller;

import com.CinephileLog.review.dto.ReviewResponse;
import com.CinephileLog.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/admin/reviews")       // 리뷰 관리
public class AdminReviewController {

    private final ReviewService reviewService;

    @Autowired
    public AdminReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public String listReviews(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<ReviewResponse> reviews = reviewService.getAllReviewsWithNickname(keyword);
        model.addAttribute("reviews", reviews);
        return "admin/review/list";
    }

    @PostMapping("/delete/{reviewId}")
    public String deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return "redirect:/admin/reviews";
    }

    @GetMapping("/{reviewId}")
    public String reviewDetail(@PathVariable Long reviewId, Model model) {
        ReviewResponse review = reviewService.getReviewById(reviewId);
        model.addAttribute("review", review);
        return "admin/review/detail";
    }

    @GetMapping("/search")
    public String searchReviews(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<ReviewResponse> searchResults = reviewService.getAllReviewsWithNickname(keyword);
        model.addAttribute("reviews", searchResults);
        return "admin/review/list";
    }
}