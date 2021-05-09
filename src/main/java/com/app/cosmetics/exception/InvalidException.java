package com.app.cosmetics.exception;

import org.springframework.http.HttpStatus;

public class InvalidException extends MyException {

    public InvalidException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
