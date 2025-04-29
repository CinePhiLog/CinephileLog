package com.CinephileLog.movie.controller;

import com.CinephileLog.movie.domain.Genre;
import com.CinephileLog.movie.dto.GenreRequest;
import com.CinephileLog.movie.dto.GenreResponse;
import com.CinephileLog.movie.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping("/genre")
    public ResponseEntity<GenreResponse> saveGenre(@RequestBody GenreRequest genreRequest) {
        Genre genre = genreService.saveGenre(genreRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new GenreResponse(genre));
    }

}
