package com.test.restapi.dto;

import com.test.restapi.exception.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * Unified API response envelope for all endpoints. Use success/error factories in production.
 */
public class ApiResponse<T> {

    private boolean success;
    private int status;
    private String message;
    private T data;
    private Object errors;

    public ApiResponse(boolean success, int status, String message, T data, Object errors) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, HttpStatus.OK.value(), message, data, null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return success("Success", data);
    }

    public static <T> ApiResponse<T> error(ErrorCode code, String message, Object errors) {
        return new ApiResponse<>(false, code.getStatus(),
                message != null ? message : code.getDefaultMessage(), null, errors);
    }

    public static <T> ApiResponse<T> error(ErrorCode code, Object errors) {
        return error(code, code.getDefaultMessage(), errors);
    }

    public static <T> ApiResponse<T> error(int status, String message, Object errors) {
        return new ApiResponse<>(false, status, message, null, errors);
    }

    // Getters and setters
    public boolean isSuccess() { return success; }
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public Object getErrors() { return errors; }

    public void setSuccess(boolean success) { this.success = success; }
    public void setStatus(int status) { this.status = status; }
    public void setMessage(String message) { this.message = message; }
    public void setData(T data) { this.data = data; }
    public void setErrors(Object errors) { this.errors = errors; }
}
