package com.CinephileLog.movie.service;

import com.CinephileLog.movie.domain.Movie;
import com.CinephileLog.movie.repository.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final CacheManager cacheManager;

    public MovieService(MovieRepository movieRepository, CacheManager cacheManager) {
        this.movieRepository = movieRepository;
        this.cacheManager = cacheManager;
    }

    // 캐시에서 Movie 객체 가져오는 메서드
    private Movie getMovieFromCache(Long movieId) {
        // 캐시에서 값을 가져옵니다
        Object cachedValue = cacheManager.getCache("movieDetail").get(movieId, Object.class);

        if (cachedValue != null) {
            // Jackson ObjectMapper로 역직렬화하여 Movie 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(cachedValue, Movie.class);
        }
        return null;  // 캐시에서 찾을 수 없으면 null 반환
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Cacheable(cacheNames = "movieDetail", key = "#movieId")
    public Movie getMovieById(Long movieId) {
        Movie movie = getMovieFromCache(movieId);
        if (movie != null) {
            return movie;  // 캐시에서 찾은 경우 반환
        }

        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        return movieOptional.orElseGet(Movie::new);
    }
}
