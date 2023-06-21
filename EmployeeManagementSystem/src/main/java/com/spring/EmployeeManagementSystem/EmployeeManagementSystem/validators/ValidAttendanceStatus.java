package com.spring.EmployeeManagementSystem.EmployeeManagementSystem.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = AttendanceStatusValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAttendanceStatus {
    String message() default "Invalid attendance status provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}