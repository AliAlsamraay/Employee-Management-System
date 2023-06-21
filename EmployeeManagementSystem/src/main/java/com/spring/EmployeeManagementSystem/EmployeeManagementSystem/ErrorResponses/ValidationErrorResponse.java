package com.spring.EmployeeManagementSystem.EmployeeManagementSystem.ErrorResponses;

public class ValidationErrorResponse {
    private int statusCode;
    private String message;
    private long timeStamp;

    public ValidationErrorResponse() {
    }

    public ValidationErrorResponse(int statusCode, String message, long timeStamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    // setters and getters

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public ValidationErrorResponse statusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String errorMessage) {
        this.message = errorMessage;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

}
