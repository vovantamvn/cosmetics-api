package com.app.cosmetics.api.exception;

import org.springframework.http.HttpStatus;

public final class ExpiredException extends MyException {

    public ExpiredException() {
        super(HttpStatus.UNAUTHORIZED, "Expired Exception");
    }
}
