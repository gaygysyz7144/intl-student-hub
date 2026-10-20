package com.example.intlhub.exception;

import org.springframework.http.HttpStatus;

// AI-assisted: business error with an HTTP status, turned into JSON by GlobalExceptionHandler
public class ApiException extends RuntimeException {
    private final HttpStatus status;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
