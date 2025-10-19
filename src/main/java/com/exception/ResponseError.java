package com.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ResponseError {
    private HttpStatus status;
    private String message;
    private LocalDateTime time = LocalDateTime.now();

    public ResponseError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTime() {
        return time;
    }

}
