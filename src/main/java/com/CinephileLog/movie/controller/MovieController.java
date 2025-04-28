package com.CinephileLog.movie.controller;

import com.CinephileLog.movie.domain.Movie;
import com.CinephileLog.movie.dto.MovieResponse;
import com.CinephileLog.movie.service.MovieService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {
    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();

        List<MovieResponse> response = movies.stream()
                .map(movie -> new MovieResponse(movie))
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);

        return ResponseEntity.ok(new MovieResponse(movie));
    }
}
