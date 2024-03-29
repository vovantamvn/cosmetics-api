package com.app.cosmetics.api.exception;

import org.springframework.http.HttpStatus;

public final class NotFoundException extends MyException {

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND, "Not Found Exception");
    }
}
