package com.CinephileLog.movie.controller;

import com.CinephileLog.movie.domain.Movie;
import com.CinephileLog.movie.dto.MovieResponse;
import com.CinephileLog.movie.dto.MovieSearchResponse;
import com.CinephileLog.movie.service.MovieService;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        MovieResponse movie = movieService.getMovieById(id);

        return ResponseEntity.ok(movie);
    }
    @GetMapping("/api/movies/search")
    public ResponseEntity<List<MovieSearchResponse>> searchMovies(@RequestParam String keyword) {
        List<Movie> movies = movieService.searchByTitle(keyword);
        List<MovieSearchResponse> result = movies.stream()
                .map(movie -> new MovieSearchResponse(movie.getId(), movie.getTitle(), movie.getReleaseDate()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }


}
