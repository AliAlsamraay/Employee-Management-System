package com.spring.EmployeeManagementSystem.EmployeeManagementSystem.ExceptionHandlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.ErrorResponses.ValidationErrorResponse;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class RequestsHandler {
    // this function to handle any entity not found exception.
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // occurs when there is a violation of database constraints.
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationEntity(DataIntegrityViolationException ex) {
        final ValidationErrorResponse error = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // this function to handle custom validation errors.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    // this function to handle invalid request body.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ValidationErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        final ValidationErrorResponse error = new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis());

        final ResponseEntity<ValidationErrorResponse> responseEntity = new ResponseEntity<>(error,
                HttpStatus.BAD_REQUEST);

        return responseEntity;
    }

    // this function to handle all other exceptions.
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ValidationErrorResponse> handleRuntimeException(RuntimeException ex) {
        final ValidationErrorResponse error = new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(),
                ex.toString(),
                System.currentTimeMillis());

        final ResponseEntity<ValidationErrorResponse> responseEntity = new ResponseEntity<>(error,
                HttpStatus.BAD_REQUEST);

        return responseEntity;
    }

}
