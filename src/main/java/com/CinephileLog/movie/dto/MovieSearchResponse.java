package com.CinephileLog.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MovieSearchResponse {
    private Long movieId;
    private String title;
    private LocalDate releaseDate;
}
