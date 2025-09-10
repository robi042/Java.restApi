package com.test.restapi.dto;

import java.util.Map;

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
