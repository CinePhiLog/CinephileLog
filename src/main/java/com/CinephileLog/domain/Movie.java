package com.CinephileLog.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Movie {

    @Id
    private String title;

    private String releaseDate;
    private String runtime;
    private String language;
    private String cast;
    private String director;
    private String genre;
    private String synopsis;
}