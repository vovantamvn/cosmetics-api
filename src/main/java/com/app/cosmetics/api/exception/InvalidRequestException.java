package com.app.cosmetics.api.exception;

import org.springframework.validation.Errors;

public final class InvalidRequestException extends RuntimeException {
    private final Errors errors;

    public InvalidRequestException(Errors errors) {
        super();
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
