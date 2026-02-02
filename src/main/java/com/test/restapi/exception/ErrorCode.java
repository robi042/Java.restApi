package com.test.restapi.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Validation error"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Forbidden"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not found"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid credentials"),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "Email already exists"),
    JWT_EXPIRED(HttpStatus.FORBIDDEN, "JWT expired"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");

    private final HttpStatus status;
    private final String defaultMessage;

    ErrorCode(HttpStatus status, String defaultMessage) {
        this.status = status;
        this.defaultMessage = defaultMessage;
    }

    public int getStatus() {
        return status.value();
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
