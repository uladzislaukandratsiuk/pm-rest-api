package com.demo.rest.api.exception;

public class CustomEntityNotFoundException extends RuntimeException {

    public CustomEntityNotFoundException() {
        super();
    }

    public CustomEntityNotFoundException(String message) {
        super(message);
    }

    public CustomEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomEntityNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CustomEntityNotFoundException(String message, Throwable cause,
                                            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
