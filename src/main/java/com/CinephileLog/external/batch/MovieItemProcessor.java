package com.CinephileLog.external.batch;

import com.CinephileLog.external.service.TmdbApiClient;
import com.CinephileLog.movie.domain.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

@RequiredArgsConstructor
public class MovieItemProcessor implements ItemProcessor<Integer, Movie> {

    private final TmdbApiClient tmdbApiClient;
    private final String apiKey;

    @Override
    public Movie process(Integer id) {
        return tmdbApiClient.fetchMovieById(id, apiKey).orElse(null);
    }
}
