package com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Exceptions;

public class AttendanceMarkedException extends RuntimeException {
    public AttendanceMarkedException(String message) {
        super(message);
    }

    public AttendanceMarkedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AttendanceMarkedException(Throwable cause) {
        super(cause);
    }

}
