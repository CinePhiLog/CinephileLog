package com.CinephileLog.movie.service;

import com.CinephileLog.movie.domain.Movie;
import com.CinephileLog.movie.dto.MovieResponse;
import com.CinephileLog.movie.repository.MovieRepository;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "movie", key = "#id")
    public MovieResponse getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("영화 없음"));

        // 여기서 Session 살아있을 때 MovieResponse 변환
        return new MovieResponse(movie);
    }

    public MovieResponse getMovieDetail(Long id) {
        return movieRepository.findById(id)
                .map(movie -> new MovieResponse(movie))
                .orElseThrow(() -> new EntityNotFoundException("영화를 찾을 수 없습니다. id=" + id));
    }
}
