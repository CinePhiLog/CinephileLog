package com.CinephileLog.review.service;

import com.CinephileLog.domain.User;
import com.CinephileLog.movie.domain.Movie;
import com.CinephileLog.movie.repository.MovieRepository;
import com.CinephileLog.repository.UserRepository;
import com.CinephileLog.review.domain.Review;
import com.CinephileLog.review.dto.ReviewRequest;
import com.CinephileLog.review.dto.ReviewRequestUpdate;
import com.CinephileLog.review.dto.ReviewResponse;
import com.CinephileLog.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.sql.BaseSqmToSqlAstConverter.AdditionalInsertValues;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository,
                         MovieRepository movieRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    // 리뷰 생성(로그인 한 유저만)
    @Transactional
    public ReviewResponse createReview(Long movieId, ReviewRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저 없음"));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("영화 없음"));

        if(reviewRepository.existsByUser_UserIdAndMovie_id(userId, movie.getId())) {
            throw new IllegalArgumentException("이미 해당 영화에 대한 리뷰를 작성했습니다");
        }

        Review savedReview = reviewRepository.save(request.toEntity(user, movie));

        updateMovieRating(movie);

        return new ReviewResponse(savedReview);
    }

    // 특정 영화의 모든 리뷰 조회
    public List<ReviewResponse> getAllReviews(Long movieId) {
        movieRepository.findById(movieId)
                .orElseThrow(() -> new NoSuchElementException("해당 하는 영화 없음"));

        List<Review> reviews = reviewRepository.findAllByMovie_Id(movieId);

        return reviews.stream()
                .map(ReviewResponse::new)
                .toList();
    }

    // 특정 유저가 작성한 리뷰들 조회
    public List<ReviewResponse> getUserReviews(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저 없음"));

        List<Review> reviews = reviewRepository.findAllByUser_UserId(userId);

        return reviews.stream()
                .map(ReviewResponse::new)
                .toList();
    }

    // 특정 영화, 유저에 해당하는 리뷰 조회
    public ReviewResponse getReview(Long userId, Long movieId) {
        movieRepository.findById(movieId)
                .orElseThrow(() -> new NoSuchElementException("해당 하는 영화 없음"));

        userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저 없음"));

        Review review = reviewRepository.findByUser_UserIdAndMovie_id(userId, movieId);

        return new ReviewResponse(review);
    }

    // 특정 영화에서 유저 닉네임으로 작성한 리뷰 조회
    public ReviewResponse getReviewByNickname(Long movieId, String nickname) {
        movieRepository.findById(movieId)
                .orElseThrow(() -> new NoSuchElementException("해당 영화 없음"));

        User user = userRepository.findByNickname(nickname);
        if(user == null) {
            throw new NoSuchElementException("해당 닉네임의 유저가 없습니다");
        }

        Review review = reviewRepository.findByMovie_IdAndUser_Nickname(movieId, nickname);

        return new ReviewResponse(review);
    }

    // 유저 닉네임으로 된 모든 리뷰 조회
    public List<ReviewResponse> getAllReviewsWithNickname(String nickname) {
        List<Review> reviews = reviewRepository.findAllByUser_Nickname(nickname);

        return reviews.stream()
                .map(ReviewResponse::new)
                .toList();
    }

    // reviewId로 리뷰 조회
    public ReviewResponse getReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 id의 리뷰 없음"));

        return new ReviewResponse(review);
    }

    // 해당하는 id의 리뷰 삭제(admin)
    @Transactional
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    // 리뷰 삭제(로그인 한 유저)
    public void deleteReviewByUser(Long movieId, Long userId) {
        Review review = reviewRepository.findByUser_UserIdAndMovie_id(userId, movieId);

        if(review == null) {
            throw new NoSuchElementException("해당하는 리뷰 없음");
        }

        reviewRepository.delete(review);
    }

    // 리뷰 수정(로그인 한 유저)
    @Transactional
    public ReviewResponse updateReview(Long movieId, ReviewRequestUpdate request, Long userId) {
        Review review = reviewRepository.findByUser_UserIdAndMovie_id(userId, movieId);

        if(review == null) {
            throw new NoSuchElementException("해당하는 리뷰 없음");
        }

        review.setContent(request.getContent());
        review.setRating(request.getRating());

        Movie movie = review.getMovie();
        updateMovieRating(movie);

        return new ReviewResponse(review);
    }

    // movie 평점에 모든 리뷰들의 평점을 업로드
    private void updateMovieRating(Movie movie) {
        List<Review> reviews = reviewRepository.findAllByMovie_Id(movie.getId());

        if(reviews.isEmpty()) {
            movie.setRating(BigDecimal.ZERO);
        } else {
            BigDecimal averageRating = reviews.stream()
                    .map(Review::getRating)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(reviews.size()), 2, RoundingMode.HALF_UP);

            movie.setRating(averageRating.setScale(1, RoundingMode.HALF_UP));
        }

        log.info("영화 평점 업데이트: {}", movie.getRating());

        movieRepository.save(movie);
    }
}
