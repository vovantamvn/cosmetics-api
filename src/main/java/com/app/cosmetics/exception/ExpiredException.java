package com.app.cosmetics.exception;

import org.springframework.http.HttpStatus;

public class ExpiredException extends MyException {

    public ExpiredException() {
        super(HttpStatus.UNAUTHORIZED, "Expired Exception");
    }
}
