package com.CinephileLog.movie.dto;

import com.CinephileLog.movie.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GenreResponse {
    private Long genreId;
    private String genreName;

    public GenreResponse(Genre genre) {
        this.genreId = genre.getId();
        this.genreName = genre.getName();
    }
}