package com.example.backend.exceptions;

public class GeocodingException extends RuntimeException {
    public GeocodingException(String message, Throwable cause) {
        super(message, cause);
    }
}
