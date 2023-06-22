package com.spring.EmployeeManagementSystem.Exceptions;

public class InvalidAttendanceStatusException extends RuntimeException {

    public InvalidAttendanceStatusException(String message) {
        super(message);
    }
}
