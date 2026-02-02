package com.test.restapi.exception;

import org.springframework.http.HttpStatus;

/**
 * Application exception for controlled API errors. Use ErrorCode for consistent status and messages.
 */
public class AppException extends RuntimeException {

    private final int status;
    private final Object errors;
    private final ErrorCode errorCode;

    public AppException(ErrorCode code) {
        super(code.getDefaultMessage());
        this.errorCode = code;
        this.status = code.getStatus();
        this.errors = null;
    }

    public AppException(ErrorCode code, String message) {
        super(message);
        this.errorCode = code;
        this.status = code.getStatus();
        this.errors = null;
    }

    public AppException(ErrorCode code, String message, Object errors) {
        super(message);
        this.errorCode = code;
        this.status = code.getStatus();
        this.errors = errors;
    }

    /** @deprecated Prefer AppException(ErrorCode, String) for production */
    public AppException(int status, String message) {
        super(message);
        this.errorCode = null;
        this.status = status;
        this.errors = null;
    }

    public AppException(int status, String message, Object errors) {
        super(message);
        this.errorCode = null;
        this.status = status;
        this.errors = errors;
    }

    public int getStatus() { return status; }
    public Object getErrors() { return errors; }
    public ErrorCode getErrorCode() { return errorCode; }
}
