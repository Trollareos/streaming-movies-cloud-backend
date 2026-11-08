package com.example.streamingmovies.repository;

import com.example.streamingmovies.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, String> {

    boolean existsByTitleIgnoreCaseAndProductionYearAndDirectorIgnoreCase(
            String title,
            Integer productionYear,
            String director
    );
}