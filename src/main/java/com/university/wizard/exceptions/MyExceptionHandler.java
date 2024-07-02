package com.university.wizard.exceptions;

import jakarta.persistence.ElementCollection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;

@RestControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> resolveException(ResourceAlreadyExistsException ex) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage());

        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<ApiResponse> resolveException(InvalidJwtException ex) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage());

        return new ResponseEntity<>(apiResponse, ex.getStatusCode());
    }
}

