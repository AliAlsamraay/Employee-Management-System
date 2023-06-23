package com.spring.EmployeeManagementSystem.Exceptions;

public class InvalidLeaveRequestStatusException extends RuntimeException {

    public InvalidLeaveRequestStatusException(String message) {
        super(message);
    }

}
