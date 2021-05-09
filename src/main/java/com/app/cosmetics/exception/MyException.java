package com.app.cosmetics.exception;

import org.springframework.http.HttpStatus;

public class MyException extends RuntimeException {
    private final HttpStatus httpStatus;

    public MyException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
