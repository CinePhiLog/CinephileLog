package com.CinephileLog.controller;

import com.CinephileLog.domain.User;
import com.CinephileLog.external.TmdbClient;
import com.CinephileLog.external.dto.TmdbMovie;
import com.CinephileLog.movie.dto.MovieResponse;
import com.CinephileLog.movie.service.MovieService;
import com.CinephileLog.review.dto.ReviewResponse;
import com.CinephileLog.review.service.ReviewService;
import com.CinephileLog.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class HomeViewController {

    private final UserService userService;
    private final MovieService movieService;
    private final ReviewService reviewService;
    private final TmdbClient tmdbClient;

    public HomeViewController(UserService userService, MovieService movieService, ReviewService reviewService, TmdbClient tmdbClient) {
        this.userService = userService;
        this.movieService = movieService;
        this.reviewService = reviewService;
        this.tmdbClient = tmdbClient;
    }
    @GetMapping("/")
    public String home() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homeView(@AuthenticationPrincipal OAuth2User user, Model model) {
        if (user != null) {
            User userInfo = userService.getUserById(user.getAttribute("userId"));
            model.addAttribute("user", userInfo);
        }

        //Get top 3 movies by popularity
        List<TmdbMovie> popularMovieList = tmdbClient.fetchMovies();

        List<TmdbMovie> moviesOfTopPopularity = popularMovieList.stream()
                .limit(3)
                .toList();

        Map<Long, List<ReviewResponse>> reviewsOfTopPopularity = new HashMap<>();
        Map<Long, Integer> reviewCountOfTopPopularity = new HashMap<>();

        for (TmdbMovie movie : moviesOfTopPopularity) {
            GetMovieReviews(movie.getId(), reviewsOfTopPopularity, reviewCountOfTopPopularity);
        }

        model.addAttribute("moviesOfTopPopularity", moviesOfTopPopularity);
        model.addAttribute("reviewsOfTopPopularity", reviewsOfTopPopularity);
        model.addAttribute("reviewCountOfTopPopularity", reviewCountOfTopPopularity);


        //Get top 3 movies by review
        List<Long> topReviewMovieList = reviewService.findTop3MovieIdsByReview();
        List<MovieResponse> moviesOfTopReview = topReviewMovieList.stream()
                .map(movieService::getMovieDetail)
                .toList();

        Map<Long, List<ReviewResponse>> reviewsOfTopReview = new HashMap<>();
        Map<Long, Integer> reviewCountOfTopReview = new HashMap<>();

        for (Long movieId : topReviewMovieList) {
            GetMovieReviews(movieId, reviewsOfTopReview, reviewCountOfTopReview);
        }

        model.addAttribute("moviesOfTopReview", moviesOfTopReview);
        model.addAttribute("reviewsOfTopReview", reviewsOfTopReview);
        model.addAttribute("reviewCountOfTopReview", reviewCountOfTopReview);


        return "index";
    }

    //Get sample movie reviews and total review count per movie
    private void GetMovieReviews(Long movieId,
                                 Map<Long, List<ReviewResponse>> reviewsMap,
                                 Map<Long, Integer> reviewCountMap) {
        try {
            List<ReviewResponse> allReviews = reviewService.getAllReviews(movieId);
            List<ReviewResponse> sampleReviews = allReviews.stream()
                                                .limit(5)
                                                .toList();

            reviewsMap.put(movieId, sampleReviews);
            reviewCountMap.put(movieId, allReviews.size());
        } catch (Exception e) {
            reviewsMap.put(movieId, Collections.emptyList());
            reviewCountMap.put(movieId, 0);
        }
    }

}
