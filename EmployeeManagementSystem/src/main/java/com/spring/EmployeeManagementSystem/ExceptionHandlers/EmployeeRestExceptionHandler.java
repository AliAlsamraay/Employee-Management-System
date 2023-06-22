package com.spring.EmployeeManagementSystem.ExceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.EmployeeManagementSystem.ErrorResponses.ValidationErrorResponse;
import com.spring.EmployeeManagementSystem.Exceptions.EmailExistException;
import com.spring.EmployeeManagementSystem.Exceptions.EmployeeNotFoundException;

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

}
