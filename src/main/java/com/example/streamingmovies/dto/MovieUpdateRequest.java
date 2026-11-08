package com.example.streamingmovies.dto;

import com.example.streamingmovies.model.MovieGenre;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

public class MovieUpdateRequest {

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be a positive integer")
    private Integer durationMinutes;

    @NotEmpty(message = "At least one genre is required")
    private Set<MovieGenre> genres;

    @NotBlank(message = "Original language is required")
    @Size(min = 2, message = "Original language must contain at least 2 characters")
    @Pattern(regexp = "^[\\p{L}]+$", message = "Original language may contain only letters")
    private String originalLanguage;

    @NotNull(message = "Rating is required")
    @DecimalMin(value = "0.0", message = "Rating must be at least 0.0")
    @DecimalMax(value = "10.0", message = "Rating must be at most 10.0")
    private BigDecimal rating;

    @NotNull(message = "Seasons is required")
    @Min(value = 0, message = "Seasons must be greater than or equal to 0")
    private Integer seasons;

    @NotNull(message = "Rental cost is required")
    @DecimalMin(value = "0.01", message = "Rental cost must be a positive number")
    private BigDecimal rentalCost;

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public Set<MovieGenre> getGenres() {
        return genres;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public Integer getSeasons() {
        return seasons;
    }

    public BigDecimal getRentalCost() {
        return rentalCost;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setGenres(Set<MovieGenre> genres) {
        this.genres = genres;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public void setSeasons(Integer seasons) {
        this.seasons = seasons;
    }

    public void setRentalCost(BigDecimal rentalCost) {
        this.rentalCost = rentalCost;
    }
}