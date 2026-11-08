package com.example.streamingmovies.exception;

public class InvalidMovieDataException extends RuntimeException {

    public InvalidMovieDataException(String message) {
        super(message);
    }
}