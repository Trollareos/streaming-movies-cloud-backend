package com.example.streamingmovies.exception;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(String code) {
        super("Movie or series with code '" + code + "' was not found");
    }
}