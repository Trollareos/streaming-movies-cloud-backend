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

public class MovieCreateRequest {

    @NotBlank(message = "Movie code is required")
    @Pattern(regexp = "^[A-Za-z0-9]{10}$", message = "Movie code must contain exactly 10 alphanumeric characters")
    private String code;

    @NotBlank(message = "Title is required")
    @Size(min = 2, message = "Title must contain at least 2 characters")
    @Pattern(regexp = "^[\\p{L}0-9 -]+$", message = "Title may contain only letters, digits, spaces and hyphens")
    private String title;

    @NotBlank(message = "Director is required")
    @Size(min = 2, message = "Director must contain at least 2 characters")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Director may contain only letters and spaces")
    private String director;

    @NotNull(message = "Production year is required")
    @Min(value = 1888, message = "Production year must be at least 1888")
    private Integer productionYear;

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

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

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

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
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