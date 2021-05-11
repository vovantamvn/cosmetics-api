package com.app.cosmetics.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MyException.class)
    public ResponseEntity<String> handleException(MyException exception) {
        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(exception.getMessage());
    }
}
