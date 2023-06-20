package com.spring.EmployeeManagementSystem.EmployeeManagementSystem.ExceptionHandlers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.ErrorResponses.EmployeeErrorResponse;
import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Exceptions.EmailExistException;
import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Exceptions.EmployeeNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class EmployeeRestExceptionHandler {

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<String> handleEmployeeNotFoundException(EntityNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

        // this function to handle any validation error.
        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<EmployeeErrorResponse> handleValidationEntity(DataIntegrityViolationException ex) {
                final EmployeeErrorResponse error = new EmployeeErrorResponse(HttpStatus.BAD_REQUEST.value(),
                                ex.getMessage(), System.currentTimeMillis());
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        // this function to check if email already exist in database or not, if exist
        // throw exception.
        @ExceptionHandler(EmailExistException.class)
        public ResponseEntity<EmployeeErrorResponse> handleEmailAlreadyExistException(EmailExistException ex) {
                final EmployeeErrorResponse error = new EmployeeErrorResponse(HttpStatus.BAD_REQUEST.value(),
                                ex.getMessage(), System.currentTimeMillis());
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        // this function to handle not found exception.
        @ExceptionHandler(EmployeeNotFoundException.class)
        public ResponseEntity<EmployeeErrorResponse> handleEmployeeNotFoundException(
                        EmployeeNotFoundException ex) {
                final EmployeeErrorResponse error = new EmployeeErrorResponse(HttpStatus.NOT_FOUND.value(),
                                ex.getMessage(),
                                System.currentTimeMillis());

                final ResponseEntity<EmployeeErrorResponse> responseEntity = new ResponseEntity<>(error,
                                HttpStatus.NOT_FOUND);

                return responseEntity;
        }

        // this function to handle all other exceptions.
        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<EmployeeErrorResponse> handleRuntimeException(RuntimeException ex) {
                final EmployeeErrorResponse error = new EmployeeErrorResponse(HttpStatus.BAD_REQUEST.value(),
                                ex.getMessage(),
                                System.currentTimeMillis());

                final ResponseEntity<EmployeeErrorResponse> responseEntity = new ResponseEntity<>(error,
                                HttpStatus.BAD_REQUEST);

                return responseEntity;
        }
}
