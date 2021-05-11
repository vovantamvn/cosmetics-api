package com.app.cosmetics.api.exception;

import org.springframework.http.HttpStatus;

public class ExpiredException extends MyException {

    public ExpiredException() {
        super(HttpStatus.UNAUTHORIZED, "Expired Exception");
    }
}
