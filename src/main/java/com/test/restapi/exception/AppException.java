package com.test.restapi.exception;

import java.util.Map;

public class AppException extends RuntimeException {

    private final int status;
    private final Object errors;

    public AppException(int status, String message) {
        super(message);
        this.status = status;
        this.errors = null;
    }

    public AppException(int status, String message, Object errors) {
        super(message);
        this.status = status;
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public Object getErrors() {
        return errors;
    }
}
