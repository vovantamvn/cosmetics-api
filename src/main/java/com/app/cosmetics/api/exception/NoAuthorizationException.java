package com.app.cosmetics.api.exception;

import org.springframework.http.HttpStatus;

public class NoAuthorizationException extends MyException {

    public NoAuthorizationException() {
        super(HttpStatus.UNAUTHORIZED, "You don't have permit");
    }
}
