package com.university.wizard.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidJwtException extends RuntimeException {
    String message;
    HttpStatus statusCode;
    public InvalidJwtException(String message, HttpStatus statusCode) {
        super();
        this.message = message;
        this.statusCode = statusCode;
    }
}