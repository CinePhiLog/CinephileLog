package com.CinephileLog.external.service;

import com.CinephileLog.movie.domain.Movie;
import com.CinephileLog.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TmdbApiClient {
    private final RestTemplate restTemplate = new RestTemplate();

    private final MovieRepository movieRepository;

    public Optional<Movie> fetchMovieById(int id, String apiKey) {
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(
                    "https://api.themoviedb.org/3/movie/" + id + "?api_key=" + apiKey + "&language=ko-KR",
                    Map.class
            );

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                return Optional.empty();
            }

            Map<String, Object> data = response.getBody();

            Long movieId = Long.valueOf((Integer) data.get("id"));
            if (movieRepository.existsById(movieId)) {
                return Optional.empty();
            }

            Movie movie = new Movie();
            movie.setId(movieId);
            movie.setTitle((String) data.get("title"));
            movie.setTitleOriginal((String) data.get("original_title"));
            movie.setReleaseDate(data.get("release_date") != null ? LocalDate.parse((String) data.get("release_date")) : null);
            movie.setPosterUrl((String) data.get("poster_path"));
            movie.setSynopsis((String) data.get("overview"));
            movie.setRating((int) Math.round(((Number) data.get("vote_average")).doubleValue()));

            return Optional.of(movie);

        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("TMDB 요청 실패 (ID=" + id + "): " + e.getMessage(), e);
        }
    }


    public int fetchLatestMovieId(String apiKey) {
        ResponseEntity<Map> response = restTemplate.getForEntity(
                "https://api.themoviedb.org/3/movie/latest?api_key=" + apiKey,
                Map.class
        );
        return (Integer) response.getBody().get("id");
    }
}
