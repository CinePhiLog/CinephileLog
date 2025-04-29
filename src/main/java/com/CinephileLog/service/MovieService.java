package com.CinephileLog.service;

import com.CinephileLog.domain.Movie;
import com.CinephileLog.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // 여기가 핵심!
    public List<Movie> findMoviesByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }
}