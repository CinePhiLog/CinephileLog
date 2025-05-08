package com.CinephileLog.movie.dto;

import com.CinephileLog.movie.domain.Genre;
import com.CinephileLog.movie.domain.Movie;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {
    private Long id;
    private String title;
    private LocalDate releaseDate;
    private String posterUrl;
    private BigDecimal rating;
    private String synopsis;
    private String director;
    private String cast;
    private List<GenreResponse> genres;

    public MovieResponse(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.releaseDate = movie.getReleaseDate();
        this.posterUrl = movie.getPosterUrl();
        this.rating = movie.getRating();
        this.synopsis = movie.getSynopsis();
        this.director = movie.getDirector();
        this.cast = movie.getCast();
        this.genres = movie.getGenres().stream()
                .map(GenreResponse::new)
                .toList();
    }
}
