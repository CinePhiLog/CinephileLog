package com.CinephileLog.movie.service;

import com.CinephileLog.movie.domain.Genre;
import com.CinephileLog.movie.dto.GenreRequest;
import com.CinephileLog.movie.repository.GenreRepository;
import org.springframework.stereotype.Service;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre saveGenre(GenreRequest request) {
        return genreRepository.save(request.toEntity());
    }
}
