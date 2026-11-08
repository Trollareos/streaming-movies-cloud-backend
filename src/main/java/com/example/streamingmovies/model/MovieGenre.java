package com.example.streamingmovies.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MovieGenre {
    ACTION("Action"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    HORROR("Horror"),
    THRILLER("Thriller"),
    ROMANCE("Romance"),
    ANIMATION("Animation"),
    DOCUMENTARY("Documentary"),
    SCI_FI("Sci-fi"),
    FANTASY("Fantasy");

    private final String displayName;

    MovieGenre(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static MovieGenre fromValue(String value) {
        for (MovieGenre genre : MovieGenre.values()) {
            if (genre.displayName.equalsIgnoreCase(value)
                    || genre.name().equalsIgnoreCase(value)
                    || genre.name().replace("_", "-").equalsIgnoreCase(value)) {
                return genre;
            }
        }

        throw new IllegalArgumentException("Invalid genre: " + value);
    }
}