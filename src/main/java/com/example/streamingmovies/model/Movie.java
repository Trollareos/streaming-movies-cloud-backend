package com.example.streamingmovies.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(
        name = "movies",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uk_movie_title_year_director",
                    columnNames = {"title", "production_year", "director"}
            )
        }
)
public class Movie {

    @Id
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "director", nullable = false)
    private String director;

    @Column(name = "production_year", nullable = false)
    private Integer productionYear;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "movie_genres",
        joinColumns = @JoinColumn(name = "movie_code"),
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uk_movie_genre",
                    columnNames = {"movie_code", "genre"}
            )
        }
)
    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private Set<MovieGenre> genres = new LinkedHashSet<>();

    @Column(name = "original_language", nullable = false)
    private String originalLanguage;

    @Column(name = "rating", nullable = false, precision = 3, scale = 1)
    private BigDecimal rating;

    @Column(name = "seasons", nullable = false)
    private Integer seasons;

    @Column(name = "rental_cost", nullable = false, precision = 6, scale = 2)
    private BigDecimal rentalCost;

    public Movie() {
    }

    public Movie(String code, String title, String director, Integer productionYear,
                 Integer durationMinutes, Set<MovieGenre> genres, String originalLanguage,
                 BigDecimal rating, Integer seasons, BigDecimal rentalCost) {
        this.code = code;
        this.title = title;
        this.director = director;
        this.productionYear = productionYear;
        this.durationMinutes = durationMinutes;
        this.genres = genres;
        this.originalLanguage = originalLanguage;
        this.rating = rating;
        this.seasons = seasons;
        this.rentalCost = rentalCost;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Set<MovieGenre> getGenres() {
        return genres;
    }

    public void setGenres(Set<MovieGenre> genres) {
        this.genres = genres;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Integer getSeasons() {
        return seasons;
    }

    public void setSeasons(Integer seasons) {
        this.seasons = seasons;
    }

    public BigDecimal getRentalCost() {
        return rentalCost;
    }

    public void setRentalCost(BigDecimal rentalCost) {
        this.rentalCost = rentalCost;
    }
}