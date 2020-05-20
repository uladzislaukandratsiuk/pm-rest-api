package com.demo.rest.api.controller;

import com.demo.rest.api.exception.ActivityNotFoundException;
import com.demo.rest.api.exception.error_response.ActivityErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ActivityRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ActivityErrorResponse> handleException(ActivityNotFoundException exc) {
        ActivityErrorResponse response = new ActivityErrorResponse(
                HttpStatus.NOT_FOUND.value(), exc.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ActivityErrorResponse> handleException(Exception exc) {
        ActivityErrorResponse response = new ActivityErrorResponse(
                HttpStatus.BAD_REQUEST.value(), exc.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
