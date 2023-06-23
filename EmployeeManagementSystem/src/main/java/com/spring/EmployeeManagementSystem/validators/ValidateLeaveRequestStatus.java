package com.spring.EmployeeManagementSystem.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = LeaveRequestStatusValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateLeaveRequestStatus {
    String message() default "Invalid leave request status provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}