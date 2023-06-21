package com.spring.EmployeeManagementSystem.EmployeeManagementSystem.ExceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.ErrorResponses.ValidationErrorResponse;
import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Exceptions.EmailExistException;
import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Exceptions.EmployeeNotFoundException;

@RestControllerAdvice
public class EmployeeRestExceptionHandler {

        // this function to handle not found exception.
        @ExceptionHandler(EmployeeNotFoundException.class)
        public ResponseEntity<ValidationErrorResponse> handleEmployeeNotFoundException(
                        EmployeeNotFoundException ex) {
                final ValidationErrorResponse error = new ValidationErrorResponse(HttpStatus.NOT_FOUND.value(),
                                ex.getMessage(),
                                System.currentTimeMillis());

                final ResponseEntity<ValidationErrorResponse> responseEntity = new ResponseEntity<>(error,
                                HttpStatus.NOT_FOUND);

                return responseEntity;
        }

        // this function to check if email already exist in database or not, if exist
        // throw exception.
        @ExceptionHandler(EmailExistException.class)
        public ResponseEntity<ValidationErrorResponse> handleEmailAlreadyExistException(EmailExistException ex) {
                final ValidationErrorResponse error = new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(),
                                ex.getMessage(), System.currentTimeMillis());
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

}
