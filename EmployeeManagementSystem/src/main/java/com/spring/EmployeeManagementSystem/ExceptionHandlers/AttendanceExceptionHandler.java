package com.spring.EmployeeManagementSystem.ExceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.EmployeeManagementSystem.ErrorResponses.ValidationErrorResponse;
import com.spring.EmployeeManagementSystem.Exceptions.AttendanceMarkedException;
import com.spring.EmployeeManagementSystem.Exceptions.InvalidAttendanceStatusException;

@RestControllerAdvice
public class AttendanceExceptionHandler {

    // Handle InvalidAttendanceStatusException
    @ExceptionHandler(InvalidAttendanceStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleInvalidAttendanceStatusException(InvalidAttendanceStatusException ex) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Handle AttendanceMarkedException (when attendance is already marked)
    @ExceptionHandler(AttendanceMarkedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleAttendanceMarkedException(AttendanceMarkedException ex) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
