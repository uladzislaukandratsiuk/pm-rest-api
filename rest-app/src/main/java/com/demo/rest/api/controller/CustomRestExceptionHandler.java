package com.demo.rest.api.controller;

import com.demo.rest.api.exception.CustomEntityNotFoundException;
import com.demo.rest.api.exception.error_response.CustomEntityErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CustomEntityErrorResponse> handleException(CustomEntityNotFoundException exc) {
        CustomEntityErrorResponse response = new CustomEntityErrorResponse(
                HttpStatus.NOT_FOUND.value(), exc.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CustomEntityErrorResponse> handleException(Exception exc) {
        CustomEntityErrorResponse response = new CustomEntityErrorResponse(
                HttpStatus.BAD_REQUEST.value(), exc.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
