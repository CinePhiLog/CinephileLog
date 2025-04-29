package com.CinephileLog.movie.dto;

import com.CinephileLog.movie.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GenreRequest {
    private String name;

    public Genre toEntity() {
        return new Genre(name);
    }
}
