package com.spring.EmployeeManagementSystem.ExceptionHandlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.EmployeeManagementSystem.ErrorResponses.ValidationErrorResponse;
import com.spring.EmployeeManagementSystem.Exceptions.AccessDeniedException;
import com.spring.EmployeeManagementSystem.Exceptions.EmailExistException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class RequestsHandler {
        // this function to handle any entity not found exception.
        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
                final ValidationErrorResponse error = new ValidationErrorResponse(HttpStatus.NOT_FOUND.value(),
                                ex.getMessage(), System.currentTimeMillis());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
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

        // this function to handle access denied exception.
        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ValidationErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
                final ValidationErrorResponse error = new ValidationErrorResponse(HttpStatus.FORBIDDEN.value(),
                                ex.getMessage(),
                                System.currentTimeMillis());

                final ResponseEntity<ValidationErrorResponse> responseEntity = new ResponseEntity<>(error,
                                HttpStatus.FORBIDDEN);

                return responseEntity;
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

        // this function to handle illegal argument exception.
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ValidationErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
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

        // this function to check if email already exist in database or not, if exist
        // throw exception.
        @ExceptionHandler(EmailExistException.class)
        public ResponseEntity<ValidationErrorResponse> handleEmailAlreadyExistException(EmailExistException ex) {
                final ValidationErrorResponse error = new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(),
                                ex.getMessage(), System.currentTimeMillis());
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        // Exception handler to handle MissingServletRequestParameterException
        @ExceptionHandler(MissingServletRequestParameterException.class)
        public ResponseEntity<Object> handleMissingParameterException(MissingServletRequestParameterException ex) {
                String paramName = ex.getParameterName();
                String errorMessage = paramName + " parameter is missing";
                final ValidationErrorResponse error = new ValidationErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                errorMessage, System.currentTimeMillis());
                return ResponseEntity.badRequest().body(error);
        }

}
