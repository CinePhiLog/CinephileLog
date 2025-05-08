package com.CinephileLog.movie.dto;

import com.CinephileLog.movie.domain.Genre;
import com.CinephileLog.movie.domain.Movie;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {
    private String title;
    private LocalDate releaseDate;
    private String posterUrl;
    private String synopsis;
    private String director;
    private String cast;
    private List<Long> genreIds;

    public Movie toEntity(Set<Genre> genres) {
        return new Movie(
                null, title, releaseDate,
                posterUrl, BigDecimal.valueOf(0.0), synopsis,
                director, cast, genres
        );
    }
}
