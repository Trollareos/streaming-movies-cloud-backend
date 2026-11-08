package com.example.streamingmovies.controller;

import com.example.streamingmovies.dto.MovieCreateRequest;
import com.example.streamingmovies.dto.MovieUpdateRequest;
import com.example.streamingmovies.model.Movie;
import com.example.streamingmovies.service.MovieService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody MovieCreateRequest request) {
        Movie createdMovie = movieService.createMovie(request);
        return ResponseEntity
                .created(URI.create("/api/movies/" + createdMovie.getCode()))
                .body(createdMovie);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{code}")
    public ResponseEntity<Movie> getMovieByCode(@PathVariable String code) {
        return ResponseEntity.ok(movieService.getMovieByCode(code));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam(required = false) String q) {
        return ResponseEntity.ok(movieService.searchMovies(q));
    }

    @PutMapping("/{code}")
    public ResponseEntity<Movie> updateMovie(
            @PathVariable String code,
            @Valid @RequestBody MovieUpdateRequest request
    ) {
        return ResponseEntity.ok(movieService.updateMovie(code, request));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String code) {
        movieService.deleteMovie(code);
        return ResponseEntity.noContent().build();
    }
}