package com.CinephileLog.movie.repository;

import com.CinephileLog.movie.domain.Genre;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(String name);
}
