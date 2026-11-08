package com.example.streamingmovies.service;

import com.example.streamingmovies.dto.MovieCreateRequest;
import com.example.streamingmovies.dto.MovieUpdateRequest;
import com.example.streamingmovies.exception.DuplicateMovieException;
import com.example.streamingmovies.exception.InvalidMovieDataException;
import com.example.streamingmovies.exception.MovieNotFoundException;
import com.example.streamingmovies.model.Movie;
import com.example.streamingmovies.model.MovieGenre;
import com.example.streamingmovies.repository.MovieRepository;
import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Transactional
    public Movie createMovie(MovieCreateRequest request) {
        validateProductionYear(request.getProductionYear());

        if (movieRepository.existsById(request.getCode())) {
            throw new DuplicateMovieException("Movie or series code already exists");
        }

        boolean sameMovieExists = movieRepository
                .existsByTitleIgnoreCaseAndProductionYearAndDirectorIgnoreCase(
                        request.getTitle(),
                        request.getProductionYear(),
                        request.getDirector()
                );

        if (sameMovieExists) {
            throw new DuplicateMovieException(
                    "Movie or series with the same title, production year and director already exists"
            );
        }

        Movie movie = new Movie(
                request.getCode(),
                request.getTitle(),
                request.getDirector(),
                request.getProductionYear(),
                request.getDurationMinutes(),
                request.getGenres(),
                request.getOriginalLanguage(),
                request.getRating(),
                request.getSeasons(),
                request.getRentalCost()
        );

        return movieRepository.save(movie);
    }

    @Transactional(readOnly = true)
    public List<Movie> getAllMovies() {
        return sortMovies(movieRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Movie getMovieByCode(String code) {
        return movieRepository.findById(code)
                .orElseThrow(() -> new MovieNotFoundException(code));
    }

    @Transactional
    public Movie updateMovie(String code, MovieUpdateRequest request) {
        Movie movie = getMovieByCode(code);

        movie.setDurationMinutes(request.getDurationMinutes());
        movie.setGenres(request.getGenres());
        movie.setOriginalLanguage(request.getOriginalLanguage());
        movie.setRating(request.getRating());
        movie.setSeasons(request.getSeasons());
        movie.setRentalCost(request.getRentalCost());

        return movieRepository.save(movie);
    }

    @Transactional
    public void deleteMovie(String code) {
        Movie movie = getMovieByCode(code);
        movieRepository.delete(movie);
    }

    @Transactional(readOnly = true)
    public List<Movie> searchMovies(String query) {
        if (query == null || query.isBlank()) {
            return getAllMovies();
        }

        String q = query.toLowerCase(Locale.ROOT).trim();

        List<Movie> results = movieRepository.findAll()
                .stream()
                .filter(movie -> matchesSearch(movie, q))
                .toList();

        return sortMovies(results);
    }

    private boolean matchesSearch(Movie movie, String q) {
        return contains(movie.getCode(), q)
                || contains(movie.getTitle(), q)
                || contains(movie.getDirector(), q)
                || contains(movie.getOriginalLanguage(), q)
                || String.valueOf(movie.getProductionYear()).contains(q)
                || String.valueOf(movie.getDurationMinutes()).contains(q)
                || String.valueOf(movie.getRating()).contains(q)
                || String.valueOf(movie.getSeasons()).contains(q)
                || String.valueOf(movie.getRentalCost()).contains(q)
                || movie.getGenres().stream()
                        .map(MovieGenre::getDisplayName)
                        .anyMatch(genre -> contains(genre, q));
    }

    private boolean contains(String value, String q) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(q);
    }

    private List<Movie> sortMovies(List<Movie> movies) {
        return movies.stream()
                .sorted(
                        Comparator.comparing(this::firstGenre)
                                .thenComparing(Movie::getDirector, String.CASE_INSENSITIVE_ORDER)
                                .thenComparing(Movie::getProductionYear)
                )
                .toList();
    }

    private String firstGenre(Movie movie) {
        return movie.getGenres()
                .stream()
                .map(MovieGenre::getDisplayName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .findFirst()
                .orElse("");
    }

    private void validateProductionYear(Integer productionYear) {
        int currentYear = Year.now().getValue();

        if (productionYear > currentYear) {
            throw new InvalidMovieDataException(
                    "Production year must be less than or equal to the current year"
            );
        }
    }
}