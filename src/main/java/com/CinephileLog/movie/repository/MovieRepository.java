package com.CinephileLog.movie.repository;

import com.CinephileLog.movie.domain.Movie;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @EntityGraph(attributePaths = {"genres"}) // 'genres' 컬렉션을 즉시 로딩
    Optional<Movie> findById(Long movieId);
}
